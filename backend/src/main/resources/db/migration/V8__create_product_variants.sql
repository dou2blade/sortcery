CREATE TABLE IF NOT EXISTS product_variants (
    id BIGSERIAL PRIMARY KEY,

    product_id BIGINT NOT NULL REFERENCES products(id) ON DELETE CASCADE,

    name VARCHAR(128) NOT NULL,
    image_url VARCHAR(2048),
    
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),

    UNIQUE(product_id, name)
);
