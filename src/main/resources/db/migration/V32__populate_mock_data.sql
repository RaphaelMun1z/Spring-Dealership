-- V32__populate_mock_data.sql

-- ==========================================
-- 1. ADM (Irineu)
-- ==========================================
INSERT INTO tb_users (id, name, phone, email, password, role, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled)
VALUES ('uuid-adm-1', 'Irineu', '(11) 91234-5678', 'irineu@gmail.com', '$2a$10$0P9rooXJBsWKpHufu19Xwei7JC3QSw8C1KqfBRxB5zfMVS4RNZkEu', 'ADM', true, true, true, true);

INSERT INTO tb_adms (id) VALUES ('uuid-adm-1');

-- ==========================================
-- 2. BRANCH & BRANCH ADDRESS
-- ==========================================
INSERT INTO tb_addresses (id, street, number, complement, district, state, country, cep, city)
VALUES ('uuid-branch-addr-1', 'Other Street', 456, 'Apt 1A', 'Downtown', 'PR', 'Brazil', '12345-678', 'Paraná');

INSERT INTO tb_branches_address (id) VALUES ('uuid-branch-addr-1');

INSERT INTO tb_branches (id, name, branch_address_id, phone_number, email, manager_name, opening_hours, branch_type, status, created_at, updated_at)
VALUES ('uuid-branch-1', 'Main Branch', 'uuid-branch-addr-1', '(11) 91212-1212', 'mainbranch@example.com', 'John Doe', '8:00 AM - 6:00 PM', 'Dealership', 'Active', '2023-01-01', '2023-11-30');

-- ==========================================
-- 3. SELLERS
-- ==========================================
INSERT INTO tb_users (id, name, phone, email, password, role, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled) VALUES
                                                                                                                                                         ('uuid-seller-1', 'JOHN DOE', '(11) 98765-4321', 'john.doe@example.com', 'securePass123', 'SELLER', true, true, true, true),
                                                                                                                                                         ('uuid-seller-2', 'JANE SMITH', '(21) 98765-4321', 'jane.smith@example.com', 'securePass456', 'SELLER', true, true, true, true),
                                                                                                                                                         ('uuid-seller-3', 'ALICE JOHNSON', '(31) 98765-4321', 'alice.johnson@example.com', 'securePass789', 'SELLER', true, true, true, true),
                                                                                                                                                         ('uuid-seller-4', 'BOB MARTINEZ', '(41) 98765-4321', 'bob.martinez@example.com', 'securePass012', 'SELLER', true, true, true, true);

INSERT INTO tb_sellers (id, hire_date, salary, commission_rate, status) VALUES
                                                                            ('uuid-seller-1', '2023-06-15', 5000.0, 0.05, 'Active'),
                                                                            ('uuid-seller-2', '2022-08-10', 5500.0, 0.07, 'Active'),
                                                                            ('uuid-seller-3', '2021-04-05', 6000.0, 0.06, 'Inactive'),
                                                                            ('uuid-seller-4', '2020-12-20', 4500.0, 0.04, 'Active');

-- ==========================================
-- 4. CUSTOMER & CUSTOMER ADDRESS
-- ==========================================
INSERT INTO tb_addresses (id, street, number, complement, district, state, country, cep, city)
VALUES ('uuid-cust-addr-1', 'Main Street', 123, 'Apt 4B', 'Downtown', 'SP', 'Brazil', '01000-000', 'São Paulo');

INSERT INTO tb_customers_address (id) VALUES ('uuid-cust-addr-1');

-- client_type INDIVIDUAL = 1
INSERT INTO tb_customers (id, name, phone, email, cpf, birth_date, registration_date, client_type, valid_cnh)
VALUES ('uuid-customer-1', 'John Doe', '(12) 93456-7890', 'johndoe@example.com', '1234567890', '1990-01-01', CURRENT_DATE, 1, true);

INSERT INTO tb_customer_addresses_map (customer_id, customer_address_id)
VALUES ('uuid-customer-1', 'uuid-cust-addr-1');

-- ==========================================
-- 5. VEHICLE SPECIFIC DETAILS
-- ==========================================
INSERT INTO tb_vehicle_specific_details (id, detail) VALUES
                                                         ('uuid-vsd-1', 'Sunroof with panoramic view'),
                                                         ('uuid-vsd-2', 'Leather seats');

-- ==========================================
-- 6. CAR
-- ==========================================
-- type CAR = 1, category SEDAN = 2, fuel GASOLINE = 1, status NEW = 1, availability AVAILABLE = 1
INSERT INTO tb_vehicles (
    id, brand, model, type, category, manufacture_year, color, sale_price,
    fuel_type, infotainment_system, fuel_tank_capacity, engine_power, passenger_capacity,
    mileage, status, availability, description, number_of_cylinders, weight, last_update, branch_id
) VALUES (
             'uuid-car-1', 'Toyota', 'Corolla', 1, 2, '2020-05-15', 'Black', 30000.0,
             1, 'Premium Sound System', 50.0, 140.0, 5,
             75000.0, 1, 1, 'Excellent condition, low mileage', 4, 1300.0, CURRENT_DATE, 'uuid-branch-1'
         );

INSERT INTO tb_land_vehicles (id, transmission_type) VALUES ('uuid-car-1', 1);
-- Na seção 6. CAR

INSERT INTO tb_cars (id, doors, drive_type, trunk_capacity)
VALUES ('uuid-car-1', 4, 'FWD', 470.0);

-- ==========================================
-- 7. INVENTORY
-- ==========================================
INSERT INTO tb_inventory (id, vehicle_id, stock_entry_date, stock_exit_date, acquisition_price, profit_margin, supplier, license_plate, chassis)
VALUES ('uuid-inventory-1', 'uuid-car-1', '2024-11-20', null, 80000.0, 0.15, 'Auto Supplier Ltd.', 'ABC-1234', '1HGCM82633A123456');

-- ==========================================
-- 8. SALE & CONTRACT
-- ==========================================
INSERT INTO tb_sales (id, seller_id, customer_id, inventory_item_id, sale_date, gross_amount, net_amount, applied_discount, payment_method, installments_number, receipt)
VALUES ('uuid-sale-1', 'uuid-seller-1', 'uuid-customer-1', 'uuid-inventory-1', CURRENT_DATE, 25000.0, 24000.0, 1000.0, 'Credit Card', 12, 'RC123456');

-- payment_terms PIX = 5, contract_status SIGNED = 1
INSERT INTO tb_contracts (id, contract_number, sale_id, contract_type, contract_date, delivery_date, total_amount, payment_terms, contract_status, notes, attachments)
VALUES ('uuid-contract-1', 'CN12345', 'uuid-sale-1', 'Sale', CURRENT_DATE, CURRENT_DATE + INTERVAL '15 days', 95000.0, 5, 1, 'None', 'contract_attachment.pdf');

UPDATE tb_sales SET contract_id = 'uuid-contract-1' WHERE id = 'uuid-sale-1';

-- ==========================================
-- 9. APPOINTMENT
-- ==========================================
-- type TEST_DRIVE = 1, status PENDING = 1
INSERT INTO tb_appointments (id, date, appointment_type, appointment_status, customer_id, seller_id)
VALUES ('uuid-appt-1', CURRENT_DATE + INTERVAL '1 day', 1, 1, 'uuid-customer-1', 'uuid-seller-1');