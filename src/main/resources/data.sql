-- Очистка таблиц перед вставкой
DELETE FROM orders;
DELETE FROM customers;

-- Вставка тестовых данных в customers
INSERT INTO customers (name, surname, age, phone_number) VALUES
('Alexey', 'Ivanov', 25, '+79171234567'),
('Roman', 'Petrov', 30, '+79199876543'),
('Ivan', 'Sidorov', 35, '+79167778899'),
('Anna', 'Smirnova', 28, '+79165554433'),
('Oleg', 'Kuznetsov', 40, '+79163332211');

-- Вставка тестовых данных в orders
INSERT INTO orders (date, customer_id, product_name, amount) VALUES
('2024-01-15', 1, 'Laptop', 1500.00),
('2024-01-16', 2, 'Smartphone', 800.00),
('2024-01-17', 1, 'Headphones', 150.00),
('2024-01-18', 3, 'Tablet', 500.00),
('2024-01-19', 4, 'Monitor', 300.00),
('2024-01-20', 5, 'Keyboard', 100.00),
('2024-01-21', 2, 'Mouse', 50.00);