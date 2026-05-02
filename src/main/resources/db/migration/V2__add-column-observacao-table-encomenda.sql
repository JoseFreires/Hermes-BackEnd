-- Adiciona a coluna observacao na tabela encomenda
ALTER TABLE `hermes_db`.`encomenda` 
ADD COLUMN `observacao` VARCHAR(500) NULL AFTER `token`;