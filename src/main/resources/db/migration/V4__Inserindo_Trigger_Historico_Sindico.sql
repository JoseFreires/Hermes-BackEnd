DELIMITER $$

CREATE TRIGGER tr_historico_sindico_inicio
    AFTER INSERT ON usuario_papel
    FOR EACH ROW
BEGIN
    DECLARE v_id_papel_sindico INT;
    DECLARE v_id_pessoa INT;

    -- 1. Descobre dinamicamente o ID da ROLE_SINDICO no seu banco
    SELECT idPapel INTO v_id_papel_sindico
    FROM papel
    WHERE nome_papel = 'ROLE_SINDICO';

    -- 2. Se o papel que o ADM acabou de dar for o de Síndico, entra na lógica
    IF NEW.Papel_idPapel = v_id_papel_sindico THEN

        -- 3. Busca o ID da Pessoa dona desse usuário que ganhou a role
    SELECT Pessoa_idPessoa INTO v_id_pessoa
    FROM usuario
    WHERE idUsuario = NEW.Usuario_idUsuario;

    -- 4. Grava no histórico (Ajuste os nomes das colunas caso mude na sua tabela)
    INSERT INTO historico_sindico (data_inicio, data_fim, Pessoa_idPessoa)
    VALUES (CURDATE(), NULL, v_id_pessoa);

END IF;
END$$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER tr_historico_sindico_fim
    AFTER DELETE ON usuario_papel
    FOR EACH ROW
BEGIN
    DECLARE v_id_papel_sindico INT;
    DECLARE v_id_pessoa INT;

    -- 1. Descobre o ID da ROLE_SINDICO
    SELECT idPapel INTO v_id_papel_sindico
    FROM papel
    WHERE nome_papel = 'ROLE_SINDICO';

    -- 2. Se o papel que está sendo removido for o de Síndico
    IF OLD.Papel_idPapel = v_id_papel_sindico THEN

        -- 3. Descobre quem era a pessoa atrelada àquele usuário
    SELECT Pessoa_idPessoa INTO v_id_pessoa
    FROM usuario
    WHERE idUsuario = OLD.Usuario_idUsuario;

    -- 4. Atualiza a data fim apenas do mandato que estava em vigor (data_fim IS NULL)
    UPDATE historico_sindico
    SET data_fim = CURDATE()
    WHERE Pessoa_idPessoa = v_id_pessoa AND data_fim IS NULL;

END IF;
END$$

DELIMITER ;