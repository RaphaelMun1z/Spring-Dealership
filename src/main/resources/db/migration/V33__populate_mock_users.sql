-- ==========================================
-- REGISTRO DE ADMINISTRADOR (ADM)
-- ==========================================
-- 1. Criação do utilizador (Palavra-passe igual à do Irineu)
INSERT INTO tb_users
(id, name, phone, email, password, role, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled)
VALUES
    ('uuid-admin-001', 'ADMINISTRADOR GERAL', '(11) 99999-9999', 'admin@dealer.com', '$2a$10$0P9rooXJBsWKpHufu19Xwei7JC3QSw8C1KqfBRxB5zfMVS4RNZkEu', 'ADM', true, true, true, true);

INSERT INTO tb_adms (id) VALUES ('uuid-admin-001');

-- ==========================================
-- REGISTRO DE VENDEDOR (SELLER)
-- ==========================================
-- 1. Criação do utilizador (Palavra-passe igual à do Irineu)
INSERT INTO tb_users
(id, name, phone, email, password, role, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled)
VALUES
    ('uuid-sel-999', 'VENDEDOR DE TESTE', '(11) 98888-8888', 'vendedor.teste@dealer.com', '$2a$10$0P9rooXJBsWKpHufu19Xwei7JC3QSw8C1KqfBRxB5zfMVS4RNZkEu', 'SELLER', true, true, true, true);

INSERT INTO tb_sellers
(id, hire_date, salary, commission_rate, status)
VALUES
    ('uuid-sel-999', '2024-01-01', 5000.0, 0.05, 'Active');