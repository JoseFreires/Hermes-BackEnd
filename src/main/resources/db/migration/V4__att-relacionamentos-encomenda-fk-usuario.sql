USE `hermes_db`;

-- 1. Desativa checagem de FK para evitar travas de integridade durante a mudança
SET FOREIGN_KEY_CHECKS = 0;

-- 2. Limpa a tabela para garantir que IDs antigos não quebrem as novas FKs
TRUNCATE TABLE encomenda;

-- 3. Remove as FKs antigas APENAS se elas existirem (evita erro 1091)
SET @fk_porteiro = (SELECT CONSTRAINT_NAME FROM information_schema.TABLE_CONSTRAINTS 
                    WHERE CONSTRAINT_NAME = 'fk_enc_porteiro' AND TABLE_SCHEMA = 'hermes_db' AND TABLE_NAME = 'encomenda');
SET @query1 = IF(@fk_porteiro IS NOT NULL, 'ALTER TABLE encomenda DROP FOREIGN KEY fk_enc_porteiro', 'SELECT "FK Porteiro ja removida"');
PREPARE stmt1 FROM @query1; EXECUTE stmt1; DEALLOCATE PREPARE stmt1;

SET @fk_usuario = (SELECT CONSTRAINT_NAME FROM information_schema.TABLE_CONSTRAINTS 
                   WHERE CONSTRAINT_NAME = 'fk_encomenda_usuario' AND TABLE_SCHEMA = 'hermes_db' AND TABLE_NAME = 'encomenda');
SET @query2 = IF(@fk_usuario IS NOT NULL, 'ALTER TABLE encomenda DROP FOREIGN KEY fk_encomenda_usuario', 'SELECT "FK Usuario ja removida"');
PREPARE stmt2 FROM @query2; EXECUTE stmt2; DEALLOCATE PREPARE stmt2;

-- 4. Renomeia as colunas APENAS se elas ainda tiverem o nome antigo 
SET @col_porteiro = (SELECT COLUMN_NAME FROM information_schema.COLUMNS 
                     WHERE COLUMN_NAME = 'Porteiro_id_papel' AND TABLE_SCHEMA = 'hermes_db' AND TABLE_NAME = 'encomenda');
SET @query3 = IF(@col_porteiro IS NOT NULL, 
                 'ALTER TABLE encomenda CHANGE COLUMN Porteiro_id_papel id_usuario_porteiro INT NOT NULL', 
                 'SELECT "Coluna Porteiro ja renomeada"');
PREPARE stmt3 FROM @query3; EXECUTE stmt3; DEALLOCATE PREPARE stmt3;

SET @col_morador = (SELECT COLUMN_NAME FROM information_schema.COLUMNS 
                    WHERE COLUMN_NAME = 'id_usuario_destinatario' AND TABLE_SCHEMA = 'hermes_db' AND TABLE_NAME = 'encomenda');
SET @query4 = IF(@col_morador IS NOT NULL, 
                 'ALTER TABLE encomenda CHANGE COLUMN id_usuario_destinatario id_usuario_morador INT NOT NULL', 
                 'SELECT "Coluna Morador ja renomeada"');
PREPARE stmt4 FROM @query4; EXECUTE stmt4; DEALLOCATE PREPARE stmt4;

-- 5. Cria as novas Foreign Keys padronizadas
ALTER TABLE encomenda 
ADD CONSTRAINT fk_encomenda_porteiro_user FOREIGN KEY (id_usuario_porteiro) REFERENCES usuario (idUsuario),
ADD CONSTRAINT fk_encomenda_morador_user FOREIGN KEY (id_usuario_morador) REFERENCES usuario (idUsuario);

-- 6. Reativa as checagens
SET FOREIGN_KEY_CHECKS = 1;