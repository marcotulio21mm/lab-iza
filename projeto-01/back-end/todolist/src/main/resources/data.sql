DROP TABLE IF EXISTS TB_TAREFA;

CREATE TABLE TB_TAREFA (
    id BIGSERIAL  PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    status BOOLEAN NOT NULL DEFAULT FALSE,
    prioridade VARCHAR(50) NOT NULL DEFAULT  'BAIXA',
    tipo VARCHAR(20) NOT NULL DEFAULT  'LIVRE',
    tarefa VARCHAR(20) NOT NULL DEFAULT  'LIVRE',
    local_date DATE DEFAULT  null
);

INSERT INTO TB_TAREFA (id, titulo, status, prioridade, tipo) VALUES (1, 'Estudar para o exame', false, 'BAIXA','LIVRE'),
                                                  (2, 'Fazer compras', true, 'BAIXA','LIVRE'),
                                                  (3, 'Ir ao médico', false, 'BAIXA','LIVRE'),
                                                  (4, 'Enviar relatório', true, 'BAIXA','LIVRE'),
                                                  (5, 'Consultar nota no sga', true, 'BAIXA','LIVRE');

INSERT INTO TB_TAREFA (id, titulo, status, prioridade, tipo, local_date, tarefa) VALUES(6, 'Limpar a casa', false, 'ALTA', 'DATA','2024-10-11', 'PRAZO'),
                                                                   (7, 'Preparar apresentação', true, 'MEDIA', 'DATA','2024-04-13', 'DATA'),
                                                                   (8, 'Fazer exercícios', false, 'ALTA', 'DATA','2024-06-01', 'DATA'),
                                                                   (9, 'Estudar para prova', true, 'MEDIA', 'DATA','2024-07-29', 'PRAZO'),
                                                                   (10, 'Resolver pendências', true, 'BAIXA', 'DATA','2024-09-20', 'PRAZO');


