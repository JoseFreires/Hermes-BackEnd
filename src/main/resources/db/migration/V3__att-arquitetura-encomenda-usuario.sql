-- Se tiverem com registros ocultos de encomenda, utilizem:
--SET SQL_SAFE_UPDATES = 0;
--DELETE FROM encomenda;
--SET SQL_SAFE_UPDATES = 1;

-- Padronizando o Enum (No Spring esta sem acento já no banco estava)

ALTER TABLE `porteiro`
    MODIFY COLUMN `turno` ENUM('MANHA', 'TARDE', 'NOITE') NOT NULL;

-- Ajusta a Encomenda para a nova lógica de Usuário Destinatário
-- Removendo FK MORADOR
ALTER TABLE `encomenda`
DROP FOREIGN KEY `fk_enc_morador`;
-- Removendo idPapel (Que não vamos utilizar)
ALTER TABLE `encomenda`
DROP COLUMN `Morador_id_papel`;

-- Adicionando a Coluna Id_Usuario_destinatário
ALTER TABLE `encomenda`
    ADD COLUMN `id_usuario_destinatario` INT NOT NULL AFTER `token`;
-- Adicionando FK
ALTER TABLE `encomenda`
    ADD CONSTRAINT `fk_encomenda_usuario`
    FOREIGN KEY (`id_usuario_destinatario`)
    REFERENCES `usuario` (`idUsuario`);