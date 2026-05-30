
ALTER TABLE usuario DROP FOREIGN KEY fk_usuario_pessoa;

ALTER TABLE usuario DROP INDEX Pessoa_idPessoa_UNIQUE;

ALTER TABLE usuario ADD INDEX fk_usuario_pessoa_idx (Pessoa_idPessoa ASC);

ALTER TABLE usuario ADD CONSTRAINT fk_usuario_pessoa
    FOREIGN KEY (Pessoa_idPessoa) REFERENCES pessoa (idPessoa) ON DELETE CASCADE;