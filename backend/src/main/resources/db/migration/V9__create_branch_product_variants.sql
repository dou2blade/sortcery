CREATE TABLE IF NOT EXISTS branch_product_variants (
    id BIGSERIAL PRIMARY KEY,

    branch_id BIGINT NOT NULL REFERENCES branches(id) ON DELETE RESTRICT,
    product_variant_id BIGINT NOT NULL REFERENCES product_variants(id) ON DELETE RESTRICT,

    sku VARCHAR(128) NOT NULL,

    price NUMERIC(10, 2) NOT NULL
        CHECK (price >= 0),

    quantity INTEGER NOT NULL DEFAULT 0
        CHECK (quantity >= 0),
    
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),

    UNIQUE(branch_id, product_variant_id),
    UNIQUE(branch_id, sku)
);
