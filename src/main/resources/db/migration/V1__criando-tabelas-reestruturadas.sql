-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

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
  `idTorre` INT NOT NULL AUTO_INCREMENT,
  `nome_torre` VARCHAR(45) NOT NULL,
  `Condominio_idCondominio` INT NOT NULL,
  PRIMARY KEY (`idTorre`),
  INDEX `fk_bloco_condominio` (`Condominio_idCondominio` ASC) VISIBLE,
  CONSTRAINT `fk_bloco_condominio`
    FOREIGN KEY (`Condominio_idCondominio`)
    REFERENCES `hermes_db`.`condominio` (`idCondominio`))
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
    REFERENCES `hermes_db`.`bloco` (`idTorre`))
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
  UNIQUE INDEX `nome_papel` (`nome_papel` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hermes_db`.`morador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`morador` (
  `id_papel` INT NOT NULL,
  `moradia_idMoradia` INT NOT NULL,
  `data_chegada` DATE NOT NULL,
  `data_saida` DATE NULL DEFAULT NULL,
  `foto_perfil` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_papel`),
  INDEX `fk_morador_moradia` (`moradia_idMoradia` ASC) VISIBLE,
  CONSTRAINT `fk_morador_moradia`
    FOREIGN KEY (`moradia_idMoradia`)
    REFERENCES `hermes_db`.`moradia` (`idMoradia`),
  CONSTRAINT `fk_morador_papel`
    FOREIGN KEY (`id_papel`)
    REFERENCES `hermes_db`.`papel` (`idPapel`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hermes_db`.`porteiro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`porteiro` (
  `id_papel` INT NOT NULL,
  `turno` ENUM('MANHÃ', 'TARDE', 'NOITE') NOT NULL,
  `empresa_responsavel` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_papel`),
  CONSTRAINT `fk_porteiro_papel`
    FOREIGN KEY (`id_papel`)
    REFERENCES `hermes_db`.`papel` (`idPapel`)
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
  `Morador_id_papel` INT NOT NULL,
  `Porteiro_id_papel` INT NOT NULL,
  `tipo_retirada` ENUM('TERCEIRO', 'MORADOR', 'AUTORIZADA') NULL,
  PRIMARY KEY (`idEncomenda`),
  INDEX `fk_enc_morador` (`Morador_id_papel` ASC) VISIBLE,
  INDEX `fk_enc_porteiro` (`Porteiro_id_papel` ASC) VISIBLE,
  CONSTRAINT `fk_enc_morador`
    FOREIGN KEY (`Morador_id_papel`)
    REFERENCES `hermes_db`.`morador` (`id_papel`),
  CONSTRAINT `fk_enc_porteiro`
    FOREIGN KEY (`Porteiro_id_papel`)
    REFERENCES `hermes_db`.`porteiro` (`id_papel`))
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
  UNIQUE INDEX `client_id` (`nome_conta` ASC) VISIBLE)
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
  UNIQUE INDEX `cpf` (`cpf` ASC) VISIBLE,
  UNIQUE INDEX `email` (`email` ASC) VISIBLE)
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
  UNIQUE INDEX `username` (`username` ASC) VISIBLE,
  UNIQUE INDEX `Pessoa_idPessoa` (`Pessoa_idPessoa` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_pessoa`
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
-- Table `hermes_db`.`pessoa_autorizada`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`pessoa_autorizada` (
  `idPesssoaAutorizada` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `cpf` VARCHAR(11) NOT NULL,
  `Morador_id_papel` INT NOT NULL,
  PRIMARY KEY (`idPesssoaAutorizada`),
  INDEX `fk_confiavel_morador` (`Morador_id_papel` ASC) VISIBLE,
  CONSTRAINT `fk_confiavel_morador`
    FOREIGN KEY (`Morador_id_papel`)
    REFERENCES `hermes_db`.`morador` (`id_papel`)
    ON DELETE CASCADE)
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


-- -----------------------------------------------------
-- Table `hermes_db`.`sindico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hermes_db`.`sindico` (
  `idPapel` INT NOT NULL,
  `data_inicio_mandato` DATE NOT NULL,
  `data_fim_mandato` DATE NULL,
  PRIMARY KEY (`idPapel`),
  CONSTRAINT `fk_sindico_papel1`
    FOREIGN KEY (`idPapel`)
    REFERENCES `hermes_db`.`papel` (`idPapel`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
