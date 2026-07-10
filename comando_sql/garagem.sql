CREATE DATABASE garagem;
USE garagem;

CREATE TABLE funcionario(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf CHAR(11) UNIQUE,
    cargo ENUM('gerente', 'atendente', 'mecanico'),
    telefone VARCHAR(15),
    email VARCHAR(255) UNIQUE,
    data_admissao DATE NOT NULL,
    senha_hash VARCHAR(255) NOT NULL
);

CREATE TABLE tipo_servico(
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    valor_servico DECIMAL(10,2)
);

CREATE TABLE item(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    codigo VARCHAR(30) UNIQUE,
    marca VARCHAR(255) NOT NULL,
    quantidade INT NOT NULL,
    valor_compra DECIMAL(10, 2) NOT NULL,
    valor_venda DECIMAL(10, 2) NOT NULL
);

CREATE TABLE cliente(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf CHAR(11) UNIQUE,
    telefone VARCHAR(15),
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE veiculo(
    id INT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(7) UNIQUE,
    marca VARCHAR(255) NOT NULL,
    modelo VARCHAR(255) NOT NULL
);

CREATE TABLE cliente_veiculo(
    id_cliente INT NOT NULL,
    id_veiculo INT NOT NULL,
    PRIMARY KEY(id_cliente, id_veiculo),
    
    FOREIGN KEY(id_cliente) REFERENCES cliente(id),
    FOREIGN KEY(id_veiculo) REFERENCES veiculo(id)
);

CREATE TABLE ordem_servico(
    id INT AUTO_INCREMENT PRIMARY KEY,
    problema TEXT NOT NULL,
    estado ENUM('nao_pago', 'pago', 'concluido', 'cancelado'),
	descricao TEXT NOT NULL,
    data_registro DATE NOT NULL,
    id_veiculo INT,
    id_funcionario_responsavel INT,
    
    FOREIGN KEY(id_veiculo) REFERENCES veiculo(id),
    FOREIGN KEY(id_funcionario_responsavel) REFERENCES funcionario(id)
);

CREATE TABLE servico_aplicado(
    id_ordem_servico INT NOT NULL,
    id_tipo_servico INT NOT NULL,
    PRIMARY KEY(id_ordem_servico, id_tipo_servico),
    
    FOREIGN KEY(id_ordem_servico) REFERENCES ordem_servico(id),
    FOREIGN KEY(id_tipo_servico) REFERENCES tipo_servico(id)
);

CREATE TABLE item_servico(
    id_ordem_servico INT NOT NULL,
    id_item INT NOT NULL,
    id_tipo_servico INT NOT NULL,
    
    FOREIGN KEY (id_ordem_servico, id_tipo_servico) REFERENCES servico_aplicado(id_ordem_servico, id_tipo_servico),
    FOREIGN KEY (id_item) REFERENCES item(id)
);

INSERT INTO tipo_servico (descricao, valor_servico) VALUES
	('Troca de óleo', 80.00),
	('Alinhamento e balanceamento', 120.00),
	('Troca de pneu', 60.00),
	('Revisão geral', 350.00),
	('Troca de correia dentada', 280.00),
	('Troca de freios', 180.00),
	('Diagnóstico eletrônico', 150.00),
	('Troca de bateria', 70.00),
	('Suspensão e amortecedores', 250.00),
	('Troca de velas', 90.00),
	('Higienização do ar condicionado', 130.00),
	('Reparo elétrico', 200.00);