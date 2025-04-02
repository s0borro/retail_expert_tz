# Создание сущностей в БД нативно

## Console
CREATE TABLE customers (
ch3_ship_to_code VARCHAR(20) PRIMARY KEY,
ch3_ship_to_name VARCHAR(100),
chain_name VARCHAR(100)
);

CREATE TABLE products (
material_no VARCHAR(20) PRIMARY KEY,
material_desc_rus VARCHAR(200),
l3_product_category_code VARCHAR(20),
l3_product_category_name VARCHAR(100)
);

CREATE TABLE prices (
id BIGSERIAL PRIMARY KEY,
chain_name VARCHAR(100),
material_no VARCHAR(20),
regular_price_per_unit DECIMAL(10,2),
FOREIGN KEY (material_no) REFERENCES products(material_no)
);

CREATE TABLE actuals (
id BIGSERIAL PRIMARY KEY,
date DATE,
material_no VARCHAR(20),
ch3_ship_to_code VARCHAR(20),
volume_units INTEGER,
actual_sales_value DECIMAL(10,2),
promo_flag VARCHAR(10),
FOREIGN KEY (material_no) REFERENCES products(material_no),
FOREIGN KEY (ch3_ship_to_code) REFERENCES customers(ch3_ship_to_code)
);
