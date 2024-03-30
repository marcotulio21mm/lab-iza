CREATE TABLE TB_TAREFA (
                           id BIGINT PRIMARY KEY,
                           titulo VARCHAR(255) NOT NULL,
                           status BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO TB_TAREFA (id, titulo, status) VALUES (1, 'Estudar para o exame', false),
                                                  (2, 'Fazer compras', true),
                                                  (3, 'Ir ao médico', false),
                                                  (4, 'Enviar relatório', true),
                                                  (5, 'Consultar nota no sga', true);

