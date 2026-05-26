CREATE TABLE IF NOT EXISTS expenses(

id BIGINT AUTO_INCREMENT PRIMARY KEY,

purchase_date DATE NOT NULL,

merchant VARCHAR(255),

amount DECIMAL(10,2),

category VARCHAR(100),

processed_at TIMESTAMP

);