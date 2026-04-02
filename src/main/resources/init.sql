CREATE DATABASE IF NOT EXISTS shopping_cart_localization
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE shopping_cart_localization;

CREATE TABLE IF NOT EXISTS cart_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    total_items INT NOT NULL,
    total_cost DOUBLE NOT NULL,
    language VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS cart_items (
  id INT AUTO_INCREMENT PRIMARY KEY,
  cart_record_id INT,
  item_number INT NOT NULL,
  price DOUBLE NOT NULL,
  quantity INT NOT NULL,
  subtotal DOUBLE NOT NULL,
  FOREIGN KEY (cart_record_id) REFERENCES cart_records(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS localization_strings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `key` VARCHAR(100) NOT NULL,
    value VARCHAR(255) NOT NULL,
    language VARCHAR(10) NOT NULL,
    UNIQUE KEY key_lang (`key`, language)
);

-- Insert default localization strings for multiple languages
-- Use ON DUPLICATE KEY UPDATE so running the initializer multiple times is safe

INSERT INTO localization_strings (`key`, value, language) VALUES
  ('select_language', 'Select Language', 'en'),
  ('enter_items', 'Items', 'en'),
  ('create_fields', 'Create', 'en'),
  ('calculate', 'Calculate', 'en'),
  ('enter_price', 'Price', 'en'),
  ('enter_quantity', 'Quantity', 'en'),
  ('total_cost', 'Total', 'en')
ON DUPLICATE KEY UPDATE value = VALUES(value);

INSERT INTO localization_strings (`key`, value, language) VALUES
  ('select_language', 'Valitse kieli', 'fi'),
  ('enter_items', 'Tuotteet', 'fi'),
  ('create_fields', 'Luo', 'fi'),
  ('calculate', 'Laske', 'fi'),
  ('enter_price', 'Hinta', 'fi'),
  ('enter_quantity', 'Määrä', 'fi'),
  ('total_cost', 'Kokonaishinta', 'fi')
ON DUPLICATE KEY UPDATE value = VALUES(value);

INSERT INTO localization_strings (`key`, value, language) VALUES
  ('select_language', 'Välj språk', 'sv'),
  ('enter_items', 'Objekt', 'sv'),
  ('create_fields', 'Skapa', 'sv'),
  ('calculate', 'Beräkna', 'sv'),
  ('enter_price', 'Pris', 'sv'),
  ('enter_quantity', 'Antal', 'sv'),
  ('total_cost', 'Totalt', 'sv')
ON DUPLICATE KEY UPDATE value = VALUES(value);

INSERT INTO localization_strings (`key`, value, language) VALUES
  ('select_language', '言語を選択', 'ja'),
  ('enter_items', 'アイテム', 'ja'),
  ('create_fields', '作成', 'ja'),
  ('calculate', '計算', 'ja'),
  ('enter_price', '価格', 'ja'),
  ('enter_quantity', '数量', 'ja'),
  ('total_cost', '合計', 'ja')
ON DUPLICATE KEY UPDATE value = VALUES(value);

INSERT INTO localization_strings (`key`, value, language) VALUES
  ('select_language', 'اختر اللغة', 'ar'),
  ('enter_items', 'العناصر', 'ar'),
  ('create_fields', 'إنشاء', 'ar'),
  ('calculate', 'احسب', 'ar'),
  ('enter_price', 'السعر', 'ar'),
  ('enter_quantity', 'الكمية', 'ar'),
  ('total_cost', 'الإجمالي', 'ar')
ON DUPLICATE KEY UPDATE value = VALUES(value);
