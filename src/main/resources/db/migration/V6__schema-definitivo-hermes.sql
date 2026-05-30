-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema hermes_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hermes_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hermes_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `hermes_db` ;

-- -----------------------------------------------------
-- Table `hermes_db`.`condominio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`condominio` (
  `idCondominio` INT NOT NULL AUTO_INCREMENT,
  `nome_condominio` VARCHAR(120) NOT NULL,
  PRIMARY KEY (`idCondominio`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hermes_db`.`bloco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`bloco` (
  `idBloco` INT NOT NULL AUTO_INCREMENT,
  `nome_torre` VARCHAR(45) NOT NULL,
  `condominio_idCondominio` INT NOT NULL,
  PRIMARY KEY (`idBloco`),
  INDEX `fk_bloco_condominio` (`condominio_idCondominio` ASC) VISIBLE,
  CONSTRAINT `fk_bloco_condominio`
    FOREIGN KEY (`condominio_idCondominio`)
    REFERENCES `hermes_db`.`condominio` (`idCondominio`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hermes_db`.`conta_adm`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`conta_adm` (
  `idConta` INT NOT NULL AUTO_INCREMENT,
  `nome_conta` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `senha` VARCHAR(100) NOT NULL,
  `ativo` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idConta`),
  UNIQUE INDEX `nome_conta_UNIQUE` (`nome_conta` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hermes_db`.`pessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`pessoa` (
  `idPessoa` INT NOT NULL AUTO_INCREMENT,
  `nome_completo` VARCHAR(200) NOT NULL,
  `cpf` VARCHAR(11) NOT NULL,
  `email` VARCHAR(150) NOT NULL,
  `ativo` TINYINT(1) NOT NULL DEFAULT '1',
  `telefone` VARCHAR(14) NOT NULL,
  `data_nascimento` DATE NOT NULL,
  PRIMARY KEY (`idPessoa`),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hermes_db`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`usuario` (
  `idUsuario` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `Pessoa_idPessoa` INT NOT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  INDEX `fk_usuario_pessoa_idx` (`Pessoa_idPessoa` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_pessoa`
    FOREIGN KEY (`Pessoa_idPessoa`)
    REFERENCES `hermes_db`.`pessoa` (`idPessoa`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hermes_db`.`encomenda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`encomenda` (
  `idEncomenda` INT NOT NULL AUTO_INCREMENT,
  `nome_pacote` VARCHAR(120) NOT NULL,
  `data_hora_recebido` DATETIME NOT NULL,
  `data_hora_retirado` DATETIME NULL DEFAULT NULL,
  `foto_encomenda` VARCHAR(255) NOT NULL,
  `status` ENUM('RECEBIDA', 'ENTREGUE') NOT NULL,
  `token` VARCHAR(45) NOT NULL,
  `observacao` VARCHAR(500) NULL DEFAULT NULL,
  `tipo_retirada` ENUM('TERCEIRO', 'MORADOR', 'AUTORIZADA') NULL DEFAULT NULL,
  `id_usuario_porteiro` INT NOT NULL,
  `id_pessoa_destinatario` INT NOT NULL,
  PRIMARY KEY (`idEncomenda`),
  INDEX `fk_encomenda_porteiro` (`id_usuario_porteiro` ASC) VISIBLE,
  INDEX `fk_encomenda_pessoa` (`id_pessoa_destinatario` ASC) VISIBLE,
  CONSTRAINT `fk_encomenda_pessoa`
    FOREIGN KEY (`id_pessoa_destinatario`)
    REFERENCES `hermes_db`.`pessoa` (`idPessoa`),
  CONSTRAINT `fk_encomenda_porteiro`
    FOREIGN KEY (`id_usuario_porteiro`)
    REFERENCES `hermes_db`.`usuario` (`idUsuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hermes_db`.`flyway_schema_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`flyway_schema_history` (
  `installed_rank` INT NOT NULL,
  `version` VARCHAR(50) NULL DEFAULT NULL,
  `description` VARCHAR(200) NOT NULL,
  `type` VARCHAR(20) NOT NULL,
  `script` VARCHAR(1000) NOT NULL,
  `checksum` INT NULL DEFAULT NULL,
  `installed_by` VARCHAR(100) NOT NULL,
  `installed_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` INT NOT NULL,
  `success` TINYINT(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  INDEX `flyway_schema_history_s_idx` (`success` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hermes_db`.`historico_sindico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`historico_sindico` (
  `idHistorico` INT NOT NULL AUTO_INCREMENT,
  `data_inicio` DATE NOT NULL,
  `data_fim` DATE NULL DEFAULT NULL,
  `Pessoa_idPessoa` INT NOT NULL,
  PRIMARY KEY (`idHistorico`),
  INDEX `fk_historico_sindico_pessoa_idx` (`Pessoa_idPessoa` ASC) VISIBLE,
  CONSTRAINT `fk_historico_sindico_pessoa`
    FOREIGN KEY (`Pessoa_idPessoa`)
    REFERENCES `hermes_db`.`pessoa` (`idPessoa`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hermes_db`.`log_sistema`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`log_sistema` (
  `idLog` INT NOT NULL AUTO_INCREMENT,
  `acao_realizada` VARCHAR(255) NOT NULL,
  `tabela_alterada` VARCHAR(45) NULL DEFAULT NULL,
  `data_hora` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `Usuario_idUsuario` INT NULL DEFAULT NULL,
  `conta_adm_idConta` INT NULL DEFAULT NULL,
  PRIMARY KEY (`idLog`),
  INDEX `fk_log_usuario` (`Usuario_idUsuario` ASC) VISIBLE,
  INDEX `fk_log_svc` (`conta_adm_idConta` ASC) VISIBLE,
  CONSTRAINT `fk_log_svc`
    FOREIGN KEY (`conta_adm_idConta`)
    REFERENCES `hermes_db`.`conta_adm` (`idConta`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_log_usuario`
    FOREIGN KEY (`Usuario_idUsuario`)
    REFERENCES `hermes_db`.`usuario` (`idUsuario`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hermes_db`.`moradia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`moradia` (
  `idMoradia` INT NOT NULL AUTO_INCREMENT,
  `numero` VARCHAR(20) NOT NULL,
  `bloco_idBloco` INT NOT NULL,
  PRIMARY KEY (`idMoradia`),
  INDEX `fk_moradia_bloco` (`bloco_idBloco` ASC) VISIBLE,
  CONSTRAINT `fk_moradia_bloco`
    FOREIGN KEY (`bloco_idBloco`)
    REFERENCES `hermes_db`.`bloco` (`idBloco`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hermes_db`.`papel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`papel` (
  `idPapel` INT NOT NULL AUTO_INCREMENT,
  `nome_papel` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idPapel`),
  UNIQUE INDEX `nome_papel_UNIQUE` (`nome_papel` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hermes_db`.`morador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`morador` (
  `idMorador` INT NOT NULL AUTO_INCREMENT,
  `data_chegada` DATE NOT NULL,
  `data_saida` DATE NULL DEFAULT NULL,
  `foto_perfil` VARCHAR(255) NOT NULL,
  `pessoa_idPessoa` INT NOT NULL,
  `moradia_idMoradia` INT NOT NULL,
  PRIMARY KEY (`idMorador`),
  INDEX `fk_morador_pessoa1_idx` (`pessoa_idPessoa` ASC) VISIBLE,
  INDEX `fk_morador_moradia1_idx` (`moradia_idMoradia` ASC) VISIBLE,
  CONSTRAINT `fk_morador_pessoa1`
    FOREIGN KEY (`pessoa_idPessoa`)
    REFERENCES `hermes_db`.`pessoa` (`idPessoa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_morador_moradia1`
    FOREIGN KEY (`moradia_idMoradia`)
    REFERENCES `hermes_db`.`moradia` (`idMoradia`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hermes_db`.`porteiro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`porteiro` (
  `idPorteiro` INT NOT NULL AUTO_INCREMENT,
  `turno` ENUM('MANHA', 'TARDE', 'NOITE') NOT NULL,
  `empresa_responsavel` VARCHAR(45) NOT NULL,
  `pessoa_idPessoa` INT NOT NULL,
  PRIMARY KEY (`idPorteiro`),
  INDEX `fk_porteiro_pessoa1_idx` (`pessoa_idPessoa` ASC) VISIBLE,
  CONSTRAINT `fk_porteiro_pessoa1`
    FOREIGN KEY (`pessoa_idPessoa`)
    REFERENCES `hermes_db`.`pessoa` (`idPessoa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hermes_db`.`pessoa_autorizada`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`pessoa_autorizada` (
  `idPesssoaAutorizada` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `cpf` VARCHAR(11) NOT NULL,
  `morador_idMorador` INT NOT NULL,
  PRIMARY KEY (`idPesssoaAutorizada`),
  INDEX `fk_pessoa_autorizada_morador1_idx` (`morador_idMorador` ASC) VISIBLE,
  CONSTRAINT `fk_pessoa_autorizada_morador1`
    FOREIGN KEY (`morador_idMorador`)
    REFERENCES `hermes_db`.`morador` (`idMorador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hermes_db`.`usuario_papel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`usuario_papel` (
  `Usuario_idUsuario` INT NOT NULL,
  `Papel_idPapel` INT NOT NULL,
  PRIMARY KEY (`Usuario_idUsuario`, `Papel_idPapel`),
  INDEX `fk_up_papel` (`Papel_idPapel` ASC) VISIBLE,
  CONSTRAINT `fk_up_papel`
    FOREIGN KEY (`Papel_idPapel`)
    REFERENCES `hermes_db`.`papel` (`idPapel`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_up_usuario`
    FOREIGN KEY (`Usuario_idUsuario`)
    REFERENCES `hermes_db`.`usuario` (`idUsuario`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
