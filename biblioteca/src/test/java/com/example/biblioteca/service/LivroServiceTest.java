package com.example.biblioteca.service;

import com.example.biblioteca.dto.request.LivroRequest;
import com.example.biblioteca.dto.response.LivroResponse;
import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.entity.Livro;
import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.exception.IsbnAlreadyExistsException;
import com.example.biblioteca.exception.ResourceNotFoundException;
import com.example.biblioteca.repository.LivroRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @InjectMocks
    private LivroService service;

    @Mock
    private LivroRepository repository;

    @Captor
    private ArgumentCaptor<Livro> livroCaptor;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Captor
    private ArgumentCaptor<String> tituloCaptor;


    @Nested
    class cadastrarLivro {
        @Test
        @DisplayName("Deve cadastrar livro com sucesso")
        void deveCadastrarLivroComSucesso(){
            //arrange
            LivroRequest request = new LivroRequest("Harry Potter" , "1234567910", 1997,3,1L);
            Autor autor = new Autor(1L , "J.K Rowling", "Americano");
            Livro livro = new Livro(1L,"Harry Potter" , "12345678910", 1997,3, autor);


            when(repository.existsByIsbn(request.isbn())).thenReturn(false);
            when(repository.save(livroCaptor.capture())).thenReturn(livro);

            //act
            var output = service.cadastrar(request);

            //assert
            var livroCaptured = livroCaptor.getValue();
            assertNotNull(output);
            assertAll(
                    () -> assertEquals(request.titulo() , livroCaptured.getTitulo()),
                    () -> assertEquals(request.isbn() , livroCaptured.getIsbn()),
                    () -> assertEquals(request.anoPublicado() , livroCaptured.getAnoPublicacao()),
                    () -> assertEquals(request.quantidade() , livroCaptured.getQuantidade())
            );

            verify(repository , times(1)).save(livroCaptured);
            verify(repository ,times(1)).existsByIsbn(request.isbn());
        }

        @Test
        @DisplayName("deve lancar excecao se isbn ja cadastrado")
        void deveLancarExcecaoSeIsbnJaCadastrado(){
            LivroRequest request = new LivroRequest("Harry Potter" , "1234567910", 1997,3,1L);

            when(repository.existsByIsbn(request.isbn())).thenReturn(true);

            assertThrows(IsbnAlreadyExistsException.class , () -> service.cadastrar(request));

            verify(repository , never()).save(any());
            verify(repository , times(1)).existsByIsbn(request.isbn());
        }
    }

    @Nested
    class buscarLivroPorid{

        @Test
        @DisplayName("Deve buscar livro por id com sucesso")
        void deveBuscarLivroPorIdComSucesso(){
            Autor autor = new Autor(1L , "J.K Rowling", "Americano");
            Livro livro = new Livro(1L,"Harry Potter" , "12345678910", 1997,3, autor);

            when(repository.findById(idCaptor.capture()))
                    .thenReturn(Optional.of(livro));

            var output = service.buscarPorId(livro.getId());

            assertNotNull(output);
            assertEquals(output.id() , idCaptor.getValue());

            verify(repository , times(1)).findById(idCaptor.capture());

        }

        @Test
        @DisplayName("Deve lancar excecao quando nao encontrar livro por id")
        void deveLancarExcecaoQuandoNaoEncontrarLivroPorId(){
            when(repository.findById(idCaptor.capture())).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class , () -> service.buscarPorId(idCaptor.capture()));
            assertEquals(0 , idCaptor.getValue());
        }
    }

    @Nested
    class buscarPorTitulo{

        @Test
        @DisplayName("Deve buscar livro por titulo com sucesso")
        void deveBuscarLivroPorTituloComSucesso(){
            Autor autor = new Autor(1L , "J.K Rowling", "Americano");
            Livro livro = new Livro(1L,"Harry Potter" , "12345678910", 1997,3, autor);
            LivroResponse response = new LivroResponse(1L , "Harry Potter" , "12345678910" , 1997 , 3 , "J.K Rowling");

            var livroList = List.of(response);

            when(repository.findByTitulo(tituloCaptor.capture())).thenReturn(livroList);

            var output = service.buscarPorTitulo(livro.getTitulo());

            var tituloCaptured = tituloCaptor.getValue();

            assertNotNull(output);
            assertEquals(output.getFirst().titulo() , tituloCaptured);
        }
    }

    @Nested
    class buscarPorAutor{
        @Test
        @DisplayName("Deve buscar livro por autorId com sucesso")
        void deveBuscarLivroPorAutorIdComSucesso(){
            Autor autor = new Autor(1L , "J.K Rowling", "Americano");
            Livro livro = new Livro(1L,"Harry Potter" , "12345678910", 1997,3, autor);
            LivroResponse response = new LivroResponse(1L , "Harry Potter" , "12345678910" , 1997 , 3 , "J.K Rowling");

            var livroList = List.of(response);
            when(repository.findByAutor(idCaptor.capture())).thenReturn(livroList);

            var output = service.buscarPorAutor(autor.getId());

            assertNotNull(output);
            assertEquals(output.getFirst().id() , idCaptor.getValue());
        }
    }

    @Nested
    class atualizarLivro {

        @Test
        void deveAtualizarLivroComSucesso() {
                Autor autor = new Autor(
                        1L,
                        "J.K Rowling",
                        "Americano"
                );

                Livro livro = new Livro(
                        1L,
                        "Harry Potter",
                        "12345678910",
                        1997,
                        3,
                        autor
                );

                LivroRequest request = new LivroRequest(
                        "Harry Potter Atualizado",
                        "12345678910",
                        1997,
                        3,
                        1L
                );

                when(repository.findById(1L))
                        .thenReturn(Optional.of(livro));

                when(repository.findByIsbn(request.isbn()))
                        .thenReturn(Optional.of(livro));

                when(repository.save(livro))
                        .thenReturn(livro);

                var output = service.atualizar(1L, request);

                assertNotNull(output);

                assertAll(
                        () -> assertEquals(request.titulo(), livro.getTitulo()),
                        () -> assertEquals(request.isbn(), livro.getIsbn()),
                        () -> assertEquals(
                                request.anoPublicado(),
                                livro.getAnoPublicacao()
                        ),
                        () -> assertEquals(
                                request.quantidade(),
                                livro.getQuantidade()
                        ),
                        () -> assertEquals(
                                request.autorId(),
                                livro.getAutor().getId()
                        )
                );

                verify(repository).findById(1L);
                verify(repository).findByIsbn(request.isbn());
                verify(repository).save(livro);
            }

            @Test
            @DisplayName("Deve lancar excecao quando livro nao encontrado")
            void deveLancarExcecaoQuandoLivroNaoEncontrado(){
                LivroRequest request = new LivroRequest(
                        "Harry Potter Atualizado",
                        "12345678910",
                        1997,
                        3,
                        1L
                );

            when(repository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class , () -> service.atualizar(1L , request));
            verify(repository , never()).findByIsbn(anyString());
            verify(repository , never()).save(any(Livro.class));
            }

            @Nested
        class deletarLivro {

            @Test
            @DisplayName("Deve deletar livro por id com sucesso")
            void deveDeletarLivroPorId() {
                Autor autor = new Autor(1L, "J.K Rowling", "Americano");
                Livro livro = new Livro(1L, "Harry Potter", "12345678910", 1997, 3, autor);

                when(repository.findById(livro.getId())).thenReturn(Optional.of(livro));
                doNothing().when(repository).delete(livroCaptor.capture());

                service.deletar(livro.getId());

                var livroCaptured = livroCaptor.getAllValues().getFirst();

                assertEquals(livro.getId(), livroCaptured.getId());

                verify(repository, times(1)).findById(livro.getId());
                verify(repository, times(1)).delete(livroCaptor.capture());
            }

            @Test
            @DisplayName("Deve lancar excecao quando nao encontrar livro por id")
            void deveLancarExcecaoQuandoNaoEncontrarLivroPorId() {
                when(repository.findById(idCaptor.capture())).thenReturn(Optional.empty());

                assertThrows(ResourceNotFoundException.class, () -> service.deletar(idCaptor.capture()));
                assertEquals(0, idCaptor.getValue());

                verify(repository, times(1)).findById(idCaptor.capture());
                verify(repository, never()).delete(any(Livro.class));
            }
        }
    }
}