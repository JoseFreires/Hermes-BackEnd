-- 1. Remover a tabela antiga
DROP TABLE IF EXISTS `hermes_db`.`pessoa_papel`;


-- Criar o índice separadamente (mais seguro no MySQL)
CREATE UNIQUE INDEX `idx_username_unique` ON `hermes_db`.`usuario` (`username`);

-- 4. Criar a tabela de ligação Many-to-Many
CREATE TABLE IF NOT EXISTS `hermes_db`.`usuario_papel` (
  `Usuario_idUsuario` INT NOT NULL,
  `Papel_idPapel` INT NOT NULL,
  PRIMARY KEY (`Usuario_idUsuario`, `Papel_idPapel`),
  CONSTRAINT `fk_up_usuario`
    FOREIGN KEY (`Usuario_idUsuario`)
    REFERENCES `hermes_db`.`usuario` (`idUsuario`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_up_papel`
    FOREIGN KEY (`Papel_idPapel`)
    REFERENCES `hermes_db`.`papel` (`idPapel`)
    ON DELETE CASCADE
) ENGINE = InnoDB;

-- 5. Carga inicial de Roles
INSERT IGNORE INTO `hermes_db`.`papel` (nome_papel) VALUES ('ROLE_ADMIN'), ('ROLE_PORTEIRO'), ('ROLE_MORADOR');