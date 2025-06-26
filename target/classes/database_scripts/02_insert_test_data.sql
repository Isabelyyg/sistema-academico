USE sistema_academico;

-- Usuário administrador
INSERT INTO usuarios (email, senha, nome, curso, semestre, pergunta_secreta, resposta_secreta)
VALUES ('admin@alunos.utfpr.edu.br', 'Adm@1234', 'Admin', 'Ciência da Computação', 1, 
        'Qual o nome do seu primeiro pet?', 'rex');

-- Publicação de exemplo (considerando que o admin foi inserido como id 1)
INSERT INTO publicacoes (usuario_id, conteudo, tipo_midia)
VALUES (1, 'Bem-vindos ao novo sistema acadêmico!', 'TEXTO');

-- Interação de exemplo (curtida na publicação 1 pelo usuário 1)
INSERT INTO interacoes (publicacao_id, usuario_id, tipo, conteudo)
VALUES (1, 1, 'CURTIDA', NULL);

-- Outra interação de exemplo (comentário na publicação 1 pelo usuário 1)
INSERT INTO interacoes (publicacao_id, usuario_id, tipo, conteudo)
VALUES (1, 1, 'COMENTARIO', 'Muito bom! Parabéns pelo sistema.');
