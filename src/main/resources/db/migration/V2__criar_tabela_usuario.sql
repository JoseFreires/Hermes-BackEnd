CREATE TABLE IF NOT EXISTS `usuario` (
  `idUsuario` INT NOT NULL AUTO_INCREMENT, -- ID próprio da tabela
  `username` VARCHAR(100) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `Pessoa_idPessoa` INT NOT NULL,          -- O RELACIONAMENTO AQUI!
  PRIMARY KEY (`idUsuario`),
  UNIQUE INDEX `idx_usuario_pessoa` (`Pessoa_idPessoa`), -- Garante que é 1 para 1
  CONSTRAINT `fk_usuario_pessoa`
    FOREIGN KEY (`Pessoa_idPessoa`)
    REFERENCES `pessoa` (`idPessoa`)
    ON DELETE CASCADE
) ENGINE=InnoDB;