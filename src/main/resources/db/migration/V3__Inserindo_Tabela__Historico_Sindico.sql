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
    ON DELETE CASCADE
) ENGINE = InnoDB;