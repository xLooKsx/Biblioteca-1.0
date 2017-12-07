CREATE TABLE usuarios (
 matricula INTEGER   NOT NULL  unique,
 senha  VARCHAR(30) NOT NULL  ,
 nome VARCHAR(15)  NOT NULL  ,
 ultimo_nome VARCHAR(15) NOT NULL  ,
 logradouro VARCHAR(255) NOT NULL  ,
 tipo_logradouro CHAR(3) NOT NULL  ,
 comp_logradouro VARCHAR(50) ,
 telefone BIGINT  NOT NULL  ,
 email VARCHAR(100) NOT NULL  ,
 is_ativa BOOL NOT NULL,
 is_biblioteca BOOLEAN NOT NULL,
 tipo_usuario char(1),
 qtd_livro int not null,
 qtd_revista int not null,
PRIMARY KEY(matricula));

insert INTO usuarios(matricula, nome, senha, ultimo_nome, logradouro, tipo_logradouro, comp_logradouro, telefone, email, is_ativa, is_biblioteca, tipo_usuario, qtd_livro, qtd_revista)
values(1600344, 'Lucas', 'admin', 'Oliveira', 'nenhum', 'av', 'nada a declarar', 11988171725, 'x@x.com', true, true, 'F', 0, 0);

insert INTO usuarios(matricula, nome, senha, ultimo_nome, logradouro, tipo_logradouro, comp_logradouro, telefone, email, is_ativa, is_biblioteca, tipo_usuario, qtd_livro, qtd_revista)
values(1600346, 'Matheus', 'admin', 'Oliveira', 'nenhum', 'av', 'nada a declarar', 11988171725, 'x@x.com', true, false, 'A', 0, 0);

insert INTO usuarios(matricula, nome, senha, ultimo_nome, logradouro, tipo_logradouro, comp_logradouro, telefone, email, is_ativa, is_biblioteca, tipo_usuario, qtd_livro, qtd_revista)
values(1600345, 'Joao', 'admin', 'Oliveira', 'nenhum', 'av', 'nada a declarar', 11988171725, 'x@x.com', true, false, 'P', 0, 0);

CREATE TABLE emprestimo (
 idemprestimo  int auto_increment NOT NULL,
 usuarios_matricula INTEGER   NOT NULL  ,
 data_emprestimo DATE  NULL  ,
 data_devolucao DATE  NULL  ,
 acervo_idacervo integer not null,
 encerrado boolean not null,
PRIMARY KEY(idemprestimo) ,
 FOREIGN KEY(usuarios_matricula) REFERENCES usuarios(matricula),
 foreign key(acervo_idacervo) REFERENCES acervo(id_acervo));
 
 insert into emprestimo(usuarios_matricula, data_emprestimo, data_devolucao, acervo_idacervo, encerrado)
				values(1600344, '2017-06-03', '2017-06-10', 15535, false);
				
insert into emprestimo(usuarios_matricula, data_emprestimo, data_devolucao, acervo_idacervo, encerrado)
				values(1600346, '2017-06-03', '2017-06-10', 15535, false);

insert into emprestimo(usuarios_matricula, data_emprestimo, data_devolucao, acervo_idacervo, encerrado)
				values(1600345, '2017-06-03', '2017-06-10', 15535, false);				
 
 CREATE TABLE acervo (
 id_acervo INTEGER   NOT NULL UNIQUE ,  
 nome_do_livro VARCHAR(255) NOT NULL  ,
 descricao VARCHAR(255) NOT NULL  ,
 autor VARCHAR(125) NOT NULL  ,
 publicacao DATE  NULL  ,
 circ BOOL NOT NULL  ,
 edicao INTEGER  NOT NULL  ,
 editora VARCHAR(50) NOT NULL  ,
 is_emprestado BOOL  not NULL    ,
 tipo char(1) not null,
 reservado boolean not null,
PRIMARY KEY(id_acervo));

insert into acervo (id_acervo, nome_do_livro, descricao, autor, publicacao, circ, edicao, editora, is_emprestado, tipo)
  				values(31554, 'Feche bem os olhos','um livro com muitas paginas', 'alguem', '2016-08-04', true, 9,'arqueiro', false,  'L');          
insert into acervo (id_acervo, nome_do_livro, descricao, autor, publicacao, circ, edicao, editora, is_emprestado, tipo)
  				values(15535, 'Mochileiro das galaxias','não entre em panico', 'Douglas Adam', '2004-11-13', true, 12,'num sei', false,  'L');
insert into acervo (id_acervo, nome_do_livro, descricao, autor, publicacao, circ, edicao, editora, is_emprestado, tipo)                
  				values(31846, 'Senhor dos aneis','um livro com muitas paginas e legal', 'alguem', '2004-04-06', true, 65,'num sei', true,  'L');
insert into acervo (id_acervo, nome_do_livro, descricao, autor, publicacao, circ, edicao, editora, is_emprestado, tipo)                
  				values(31384, 'O restaurante no fim do universso','Não esqueça a sua toalha', 'Douglas Adam', '1959-02-04', true, 46,'num sei', true,  'L');
insert into acervo (id_acervo, nome_do_livro, descricao, autor, publicacao, circ, edicao, editora, is_emprestado, tipo)  				
                values(46846, 'Cidade do Ossos','novo', 'alguem', '2003-05-13', true, 513,'num sei', false,  'L');
insert into acervo (id_acervo, nome_do_livro, descricao, autor, publicacao, circ, edicao, editora, is_emprestado, tipo)                
  				values(31546, 'Use a cabeça','um livro com muitas paginas', 'alguem', '2016-08-04', true, 9,'arqueiro', false, 'L');   
                
insert into acervo (id_acervo, nome_do_livro, descricao, autor, publicacao, circ, edicao, editora, is_emprestado, tipo)
  				values(24652, 'epoca','antiga', 'alguem', '2016-08-04', true, 9,'arqueiro', false, 'R');          
insert into acervo (id_acervo, nome_do_livro, descricao, autor, publicacao, circ, edicao, editora, is_emprestado, tipo)
  				values(24642, 'veja','sempre limpa', 'doente', '2004-11-13', true, 12,'num sei', false, 'R');
insert into acervo (id_acervo, nome_do_livro, descricao, autor, publicacao, circ, edicao, editora, is_emprestado, tipo)                
  				values(54263, 'superinteresante','atual', 'alguem', '2004-04-06', true, 65,'num sei', true, 'R');
insert into acervo (id_acervo, nome_do_livro, descricao, autor, publicacao, circ, edicao, editora, is_emprestado, tipo)                
  				values(13512, 'manga','preto e branco', 'eu', '1959-02-04', true, 46,'num sei', true, 'R');
insert into acervo (id_acervo, nome_do_livro, descricao, autor, publicacao, circ, edicao, editora, is_emprestado, tipo)  				
                values(05664, 'casa verde','poderia ser azul', 'alguem', '2003-05-13', true, 513,'num sei', false, 'R');
insert into acervo (id_acervo, nome_do_livro, descricao, autor, publicacao, circ, edicao, editora, is_emprestado, tipo)                
  				values(24624, 'de colorir','um livro com muitas paginas', 'alguem', '2016-08-04', true, 9,'arqueiro', false, 'R');