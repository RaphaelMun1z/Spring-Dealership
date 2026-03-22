-- 1. Inserir o usuário base preenchendo TODOS os campos obrigatórios da entidade
INSERT INTO tb_users (
    id,
    name,
    phone,
    email,
    password,
    role,
    is_account_non_expired,
    is_account_non_locked,
    is_credentials_non_expired,
    is_enabled
) VALUES (
             gen_random_uuid(),
             'Admin Geral',
             '11999999999',
             'admin@auto.com',
             '$2a$12$.TOewZ7uG1dbBYe9AxORYOfWl3vqDLxE7V0LSUQlf05fVXR8.4vy.', -- Hash exato e real da senha '123456'
             'ADM',
             true,
             true,
             true,
             true
         );

-- 2. Inserir a relação na tabela filha (tb_adms)
INSERT INTO tb_adms (id)
VALUES (
           (SELECT id FROM tb_users WHERE email = 'admin@auto.com')
       );