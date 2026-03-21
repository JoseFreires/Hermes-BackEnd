-- Configurações de segurança para importação
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;

-- 1. Condominio
CREATE TABLE IF NOT EXISTS `tbl_condominio` (
    `idCondominio` INT(11) NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(120) NOT NULL,
    PRIMARY KEY (`idCondominio`))
    ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- 2. Bloco
CREATE TABLE IF NOT EXISTS `tbl_bloco` (
    `idTorre` INT(11) NOT NULL AUTO_INCREMENT,
    `nome_torre` VARCHAR(45) NOT NULL,
    `Condominio_idCondominio` INT(11) NOT NULL,
    PRIMARY KEY (`idTorre`),
    CONSTRAINT `fk_bloco_condominio`
    FOREIGN KEY (`Condominio_idCondominio`)
    REFERENCES `tbl_condominio` (`idCondominio`))
    ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- 3. Moradia
CREATE TABLE IF NOT EXISTS `tbl_moradia` (
    `idApartamento` INT(11) NOT NULL AUTO_INCREMENT,
    `numero` VARCHAR(20) NOT NULL,
    `bloco_idBloco` INT(11) NOT NULL,
    PRIMARY KEY (`idApartamento`),
    CONSTRAINT `fk_moradia_bloco`
    FOREIGN KEY (`bloco_idBloco`)
    REFERENCES `tbl_bloco` (`idTorre`))
    ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- 4. Pessoa
CREATE TABLE IF NOT EXISTS `tbl_pessoa` (
    `idPessoa` INT(11) NOT NULL AUTO_INCREMENT,
    `nome_completo` VARCHAR(200) NOT NULL,
    `cpf` VARCHAR(11) NULL DEFAULT NULL,
    `email` VARCHAR(150) NOT NULL,
    `ativo` TINYINT(1) NOT NULL DEFAULT 1,
    `telfone` VARCHAR(14) NOT NULL,
    PRIMARY KEY (`idPessoa`),
    UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC))
    ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- 5. Morador
CREATE TABLE IF NOT EXISTS `tbl_morador` (
    `Pessoa_idPessoa` INT(11) NOT NULL,
    `moradia_idMoradia` INT(11) NOT NULL,
    `data_chegada` DATE NOT NULL,
    `data_saida` DATE NULL,
    PRIMARY KEY (`Pessoa_idPessoa`),
    CONSTRAINT `fk_morador_pessoa`
    FOREIGN KEY (`Pessoa_idPessoa`)
    REFERENCES `tbl_pessoa` (`idPessoa`),
    CONSTRAINT `fk_morador_moradia`
    FOREIGN KEY (`moradia_idMoradia`)
    REFERENCES `tbl_moradia` (`idApartamento`))
    ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- 6. Porteiro
CREATE TABLE IF NOT EXISTS `tbl_porteiro` (
    `Pessoa_idPessoa` INT(11) NOT NULL,
    `turno` VARCHAR(45) NOT NULL,
    `empresa` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`Pessoa_idPessoa`),
    CONSTRAINT `fk_porteiro_pessoa`
    FOREIGN KEY (`Pessoa_idPessoa`)
    REFERENCES `tbl_pessoa` (`idPessoa`))
    ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- 7. Encomenda
CREATE TABLE IF NOT EXISTS `tbl_encomenda` (
    `idEncomenda` INT(11) NOT NULL AUTO_INCREMENT,
    `nome_pacote` VARCHAR(120) NOT NULL,
    `data_hora_recebido` DATETIME NOT NULL,
    `data_hora_retirado` DATETIME NULL DEFAULT NULL,
    `status` TINYINT(1) NOT NULL,
    `Morador_Pessoa_idPessoa` INT(11) NOT NULL,
    `Porteiro_Pessoa_idPessoa` INT(11) NOT NULL,
    PRIMARY KEY (`idEncomenda`),
    CONSTRAINT `fk_encomenda_morador`
    FOREIGN KEY (`Morador_Pessoa_idPessoa`)
    REFERENCES `tbl_morador` (`Pessoa_idPessoa`),
    CONSTRAINT `fk_encomenda_porteiro`
    FOREIGN KEY (`Porteiro_Pessoa_idPessoa`)
    REFERENCES `tbl_porteiro` (`Pessoa_idPessoa`))
    ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- 8. Mandato Sindico
CREATE TABLE IF NOT EXISTS `tbl_mandato_sindico` (
    `idMandato` INT(11) NOT NULL AUTO_INCREMENT,
    `Pessoa_idPessoa` INT(11) NOT NULL,
    `Condominio_idCondominio` INT(11) NOT NULL,
    `data_inicio` DATE NOT NULL,
    `data_fim` DATE NULL DEFAULT NULL,
    PRIMARY KEY (`idMandato`),
    CONSTRAINT `fk_mandato_pessoa`
    FOREIGN KEY (`Pessoa_idPessoa`)
    REFERENCES `tbl_pessoa` (`idPessoa`),
    CONSTRAINT `fk_mandato_condominio`
    FOREIGN KEY (`Condominio_idCondominio`)
    REFERENCES `tbl_condominio` (`idCondominio`))
    ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- 9. Papel
CREATE TABLE IF NOT EXISTS `tbl_papel` (
    `idPapel` INT(11) NOT NULL AUTO_INCREMENT,
    `nome_papel` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`idPapel`))
    ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- 10. Pessoa Papel
CREATE TABLE IF NOT EXISTS `tbl_pessoa_papel` (
    `idPessoaPapel` INT(11) NOT NULL AUTO_INCREMENT,
    `Pessoa_idPessoa` INT(11) NOT NULL,
    `Papel_idPapel` INT(11) NOT NULL,
    `Condominio_idCondominio` INT(11) NOT NULL,
    PRIMARY KEY (`idPessoaPapel`),
    CONSTRAINT `fk_pp_pessoa` FOREIGN KEY (`Pessoa_idPessoa`) REFERENCES `tbl_pessoa` (`idPessoa`),
    CONSTRAINT `fk_pp_papel` FOREIGN KEY (`Papel_idPapel`) REFERENCES `tbl_papel` (`idPapel`),
    CONSTRAINT `fk_pp_condominio` FOREIGN KEY (`Condominio_idCondominio`) REFERENCES `tbl_condominio` (`idCondominio`))
    ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;