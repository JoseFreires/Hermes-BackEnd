SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

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
  `nome` VARCHAR(120) NOT NULL,
  PRIMARY KEY (`idCondominio`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hermes_db`.`bloco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`bloco` (
  `idTorre` INT NOT NULL AUTO_INCREMENT,
  `nome_torre` VARCHAR(45) NOT NULL,
  `Condominio_idCondominio` INT NOT NULL,
  PRIMARY KEY (`idTorre`),
  INDEX `idx_bloco_condominio` (`Condominio_idCondominio` ASC) VISIBLE,
  CONSTRAINT `torre_ibfk_1`
    FOREIGN KEY (`Condominio_idCondominio`)
    REFERENCES `hermes_db`.`condominio` (`idCondominio`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hermes_db`.`pessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`pessoa` (
  `idPessoa` INT NOT NULL AUTO_INCREMENT,
  `nome_completo` VARCHAR(200) NOT NULL,
  `cpf` VARCHAR(11) NOT NULL,
  `email` VARCHAR(150) NOT NULL,
  `ativo` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '0 = inativo, 1 = ativo',
  `telefone` VARCHAR(14) NOT NULL,
  PRIMARY KEY (`idPessoa`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hermes_db`.`moradia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`moradia` (
  `idApartamento` INT NOT NULL AUTO_INCREMENT,
  `numero` VARCHAR(20) NOT NULL,
  `bloco_idBloco` INT NOT NULL,
  PRIMARY KEY (`idApartamento`),
  INDEX `idx_moradia_bloco` (`bloco_idBloco` ASC) VISIBLE,
  CONSTRAINT `apartamento_ibfk_1`
    FOREIGN KEY (`bloco_idBloco`)
    REFERENCES `hermes_db`.`bloco` (`idTorre`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hermes_db`.`morador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`morador` (
  `Pessoa_idPessoa` INT NOT NULL,
  `moradia_idMoradia` INT NOT NULL,
  `data_chegada` DATE NOT NULL,
  `data_saida` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`Pessoa_idPessoa`),
  INDEX `idx_morador_moradia` (`moradia_idMoradia` ASC) VISIBLE,
  CONSTRAINT `morador_ibfk_1`
    FOREIGN KEY (`Pessoa_idPessoa`)
    REFERENCES `hermes_db`.`pessoa` (`idPessoa`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `morador_ibfk_2`
    FOREIGN KEY (`moradia_idMoradia`)
    REFERENCES `hermes_db`.`moradia` (`idApartamento`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hermes_db`.`porteiro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`porteiro` (
  `Pessoa_idPessoa` INT NOT NULL,
  `turno` VARCHAR(45) NOT NULL,
  `empresa` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Pessoa_idPessoa`),
  CONSTRAINT `porteiro_ibfk_1`
    FOREIGN KEY (`Pessoa_idPessoa`)
    REFERENCES `hermes_db`.`pessoa` (`idPessoa`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hermes_db`.`pessoa_confiavel` (Agenda de Terceiros)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`pessoa_confiavel` (
  `idPessoaConfiavel` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `telefone` VARCHAR(14) NOT NULL,
  `Morador_Pessoa_idPessoa` INT NOT NULL,
  PRIMARY KEY (`idPessoaConfiavel`),
  INDEX `idx_confiavel_morador` (`Morador_Pessoa_idPessoa` ASC) VISIBLE,
  CONSTRAINT `fk_confiavel_morador`
    FOREIGN KEY (`Morador_Pessoa_idPessoa`)
    REFERENCES `hermes_db`.`morador` (`Pessoa_idPessoa`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hermes_db`.`encomenda` (Com Fluxo de Terceiro)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`encomenda` (
  `idEncomenda` INT NOT NULL AUTO_INCREMENT,
  `nome_pacote` VARCHAR(120) NOT NULL,
  `data_hora_recebido` DATETIME NOT NULL,
  `data_hora_retirado` DATETIME NULL DEFAULT NULL,
  `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '0 = aguardando, 1 = retirada',
  
  -- Lógica de Retirada por Terceiro
  `tipo_retirada` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1 = Morador, 2 = Terceiro',
  `token_retirada` VARCHAR(64) NULL DEFAULT NULL,
  `PessoaConfiavel_id` INT NULL DEFAULT NULL,
  
  -- Chaves Estrangeiras Originais
  `Morador_Pessoa_idPessoa` INT NOT NULL,
  `Porteiro_Pessoa_idPessoa` INT NOT NULL,
  
  PRIMARY KEY (`idEncomenda`),
  INDEX `idx_encomenda_morador` (`Morador_Pessoa_idPessoa` ASC) VISIBLE,
  INDEX `idx_encomenda_porteiro` (`Porteiro_Pessoa_idPessoa` ASC) VISIBLE,
  INDEX `idx_encomenda_confiavel` (`PessoaConfiavel_id` ASC) VISIBLE,
  
  CONSTRAINT `encomenda_ibfk_1`
    FOREIGN KEY (`Morador_Pessoa_idPessoa`)
    REFERENCES `hermes_db`.`morador` (`Pessoa_idPessoa`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `encomenda_ibfk_2`
    FOREIGN KEY (`Porteiro_Pessoa_idPessoa`)
    REFERENCES `hermes_db`.`porteiro` (`Pessoa_idPessoa`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `encomenda_ibfk_3`
    FOREIGN KEY (`PessoaConfiavel_id`)
    REFERENCES `hermes_db`.`pessoa_confiavel` (`idPessoaConfiavel`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hermes_db`.`svc_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`svc_account` (
  `idSvc` INT NOT NULL AUTO_INCREMENT,
  `client_id` VARCHAR(45) NOT NULL,
  `client_secret` VARCHAR(255) NOT NULL,
  `descricao` VARCHAR(100) NULL DEFAULT NULL,
  `ativo` TINYINT(1) NULL DEFAULT '1',
  PRIMARY KEY (`idSvc`),
  UNIQUE INDEX `client_id_UNIQUE` (`client_id` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hermes_db`.`log_sistema`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`log_sistema` (
  `idLog` INT NOT NULL AUTO_INCREMENT,
  `acao` VARCHAR(255) NOT NULL,
  `tabela_afetada` VARCHAR(45) NULL DEFAULT NULL,
  `data_hora` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `pessoa_idPessoa` INT NULL DEFAULT NULL,
  `svc_account_idSvc` INT NULL DEFAULT NULL,
  PRIMARY KEY (`idLog`),
  INDEX `fk_log_pessoa` (`pessoa_idPessoa` ASC) VISIBLE,
  INDEX `fk_log_svc` (`svc_account_idSvc` ASC) VISIBLE,
  CONSTRAINT `fk_log_pessoa`
    FOREIGN KEY (`pessoa_idPessoa`)
    REFERENCES `hermes_db`.`pessoa` (`idPessoa`)
    ON UPDATE CASCADE,
  CONSTRAINT `fk_log_svc`
    FOREIGN KEY (`svc_account_idSvc`)
    REFERENCES `hermes_db`.`svc_account` (`idSvc`)
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hermes_db`.`mandato_sindico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`mandato_sindico` (
  `idMandato` INT NOT NULL AUTO_INCREMENT,
  `Pessoa_idPessoa` INT NOT NULL,
  `Condominio_idCondominio` INT NOT NULL,
  `data_inicio` DATE NOT NULL,
  `data_fim` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`idMandato`),
  INDEX `idx_mandato_pessoa` (`Pessoa_idPessoa` ASC) VISIBLE,
  INDEX `idx_mandato_condominio` (`Condominio_idCondominio` ASC) VISIBLE,
  CONSTRAINT `mandato_sindico_ibfk_1`
    FOREIGN KEY (`Pessoa_idPessoa`)
    REFERENCES `hermes_db`.`pessoa` (`idPessoa`)
    ON UPDATE CASCADE,
  CONSTRAINT `mandato_sindico_ibfk_2`
    FOREIGN KEY (`Condominio_idCondominio`)
    REFERENCES `hermes_db`.`condominio` (`idCondominio`)
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hermes_db`.`papel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`papel` (
  `idPapel` INT NOT NULL AUTO_INCREMENT,
  `nome_papel` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idPapel`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hermes_db`.`pessoa_papel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`pessoa_papel` (
  `idPessoaPapel` INT NOT NULL AUTO_INCREMENT,
  `Pessoa_idPessoa` INT NOT NULL,
  `Papel_idPapel` INT NOT NULL,
  `Condominio_idCondominio` INT NOT NULL,
  PRIMARY KEY (`idPessoaPapel`),
  INDEX `idx_pp_pessoa` (`Pessoa_idPessoa` ASC) VISIBLE,
  INDEX `idx_pp_papel` (`Papel_idPapel` ASC) VISIBLE,
  INDEX `idx_pp_condominio` (`Condominio_idCondominio` ASC) VISIBLE,
  CONSTRAINT `pessoa_papel_ibfk_1`
    FOREIGN KEY (`Pessoa_idPessoa`)
    REFERENCES `hermes_db`.`pessoa` (`idPessoa`)
    ON UPDATE CASCADE,
  CONSTRAINT `pessoa_papel_ibfk_2`
    FOREIGN KEY (`Papel_idPapel`)
    REFERENCES `hermes_db`.`papel` (`idPapel`)
    ON UPDATE CASCADE,
  CONSTRAINT `pessoa_papel_ibfk_3`
    FOREIGN KEY (`Condominio_idCondominio`)
    REFERENCES `hermes_db`.`condominio` (`idCondominio`)
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS