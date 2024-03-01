CREATE TABLE IF NOT EXISTS wallet (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    balance DECIMAL(38, 2) NOT NULL,
    created_at datetime(6) not null,
    updated_at datetime(6)
);

CREATE TABLE IF NOT EXISTS transaction (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    wallet_id BIGINT NOT NULL,
    amount DECIMAL(38, 2) NOT NULL,
    type tinyint NOT NULL,
    created_at datetime(6) NOT NULL,
    FOREIGN KEY (wallet_id) REFERENCES wallet(id)
);