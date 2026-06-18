ALTER TABLE `hermes_db`.`conta_adm`
    ADD COLUMN `papel_idPapel` INT NULL;

ALTER TABLE `hermes_db`.`conta_adm`
    MODIFY COLUMN `papel_idPapel` INT NOT NULL,
    ADD UNIQUE INDEX `papel_idPapel_UNIQUE` (`papel_idPapel` ASC),
    ADD INDEX `fk_conta_adm_papel_idx` (`papel_idPapel` ASC),
    ADD CONSTRAINT `fk_conta_adm_papel`
    FOREIGN KEY (`papel_idPapel`)
    REFERENCES `hermes_db`.`papel` (`idPapel`)
    ON DELETE NO ACTION
        ON UPDATE NO ACTION;