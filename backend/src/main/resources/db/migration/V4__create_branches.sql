CREATE TABLE IF NOT EXISTS stores (
    id BIGSERIAL PRIMARY KEY,

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (store_id) REFERENCES stores(id),

    name VARCHAR(64) NOT NULL,

    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);