create database cadastro_pessoa;
use cadastro_pessoa;

create table pessoa(
	id       INT AUTO_INCREMENT PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    endereco VARCHAR(100) NOT NULL,
    email    VARCHAR(100) NOT NULL);

create table pessoa_fisica(
	pessoa_id INT PRIMARY KEY,
    cpf       VARCHAR(14) NOT NULL,
    FOREIGN KEY(pessoa_id) REFERENCES 
     pessoa(id) ON DELETE CASCADE);
     
create table pessoa_juridica(
	pessoa_id     INT PRIMARY KEY,
    cnpj          VARCHAR(18) NOT NULL,
    nome_fantasia VARCHAR(100),
    FOREIGN KEY (pessoa_id) REFERENCES
     pessoa(id) ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS produto (
id INT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(100) NOT NULL,
preco DECIMAL(10,2) NOT NULL,
estoque INT NOT NULL DEFAULT 0,
fornecedor_id INT NOT NULL,
CHECK (preco > 0),
CHECK (estoque >= 0),
FOREIGN KEY (fornecedor_id) REFERENCES pessoa_juridica(pessoa_id)
);

CREATE TABLE IF NOT EXISTS venda (
id INT AUTO_INCREMENT PRIMARY KEY,
data_venda DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
cliente_id INT NOT NULL,
FOREIGN KEY (cliente_id) REFERENCES pessoa_fisica(pessoa_id)
);

CREATE TABLE IF NOT EXISTS item_venda (
id INT AUTO_INCREMENT PRIMARY KEY,
venda_id INT NOT NULL,
produto_id INT NOT NULL,
quantidade INT NOT NULL,
valor_unitario DECIMAL(10,2) NOT NULL,
CHECK (quantidade > 0),
FOREIGN KEY (venda_id) REFERENCES venda(id) ON DELETE CASCADE,
FOREIGN KEY (produto_id) REFERENCES produto(id)
);


insert pessoa(nome, endereco, email)
 values('Pessoa1', 'end1','email1');
insert pessoa_fisica(pessoa_id, cpf)
 values(1,'11122233344');

insert pessoa(nome, endereco, email)
 values('Pessoa2', 'end2','email2');
 insert pessoa_juridica(pessoa_id, cnpj, 
   nome_fantasia)
   values(2,'0000000111','Nome fantasia');

select * from pessoa; 

SELECT 
 p.*, pf.cpf, pj.cnpj, pj.nome_fantasia
FROM pessoa p
 LEFT JOIN pessoa_fisica pf 
	ON p.id = pf.pessoa_id
 LEFT JOIN pessoa_juridica pj
    ON p.id = pj.pessoa_id
ORDER BY p.id;

select * from pessoa;
select * from pessoa_fisica;
select * from pessoa_juridica;

insert pessoa(nome, endereco, email)
  values('Pessoa3', 'end3','email3');

insert pessoa_fisica(pessoa_id, cpf)
 values(3,'33333333333');
 
 
 
 
 
 
 




