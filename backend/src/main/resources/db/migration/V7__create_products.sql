CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,

    product_category_id BIGINT NOT NULL REFERENCES product_categories(id) ON DELETE RESTRICT,
    brand_id BIGINT NOT NULL REFERENCES brands(id) ON DELETE RESTRICT,

    name VARCHAR(128) NOT NULL,
    description TEXT,

    image_url VARCHAR(2048),

    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),

    UNIQUE (brand_id, name)
);
