CREATE TABLE usuarios(
                         id BIGSERIAL PRIMARY KEY ,
                         nome VARCHAR(100) NOT NULL ,
                         email VARCHAR(255) NOT NULL UNIQUE ,
                         senha VARCHAR(255) NOT NULL ,
                         role VARCHAR(20) NOT NULL DEFAULT ('USER'),
                         CHECK ( role IN ('USER' , 'ADMIN'))
);

CREATE TABLE autores(
                        id BIGSERIAL PRIMARY KEY ,
                        nome VARCHAR(100) NOT NULL ,
                        nacionalidade VARCHAR (100) NOT NULL
);

CREATE TABLE livros(
                       id BIGSERIAL PRIMARY KEY ,
                       titulo VARCHAR(255) NOT NULL ,
                       isbn VARCHAR(13) NOT NULL UNIQUE ,
                       ano_publicado SMALLINT NOT NULL ,
                       quantidade INTEGER NOT NULL CHECK ( quantidade >= 0 ),

                       autor_id BIGINT NOT NULL REFERENCES autores(id)
);

CREATE TABLE emprestimos(
    id BIGSERIAL PRIMARY KEY ,
    data_emprestimo TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_devolucao DATE ,
    devolvido BOOLEAN NOT NULL DEFAULT FALSE,

    usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
    livro_id BIGINT NOT NULL REFERENCES livros(id)
);