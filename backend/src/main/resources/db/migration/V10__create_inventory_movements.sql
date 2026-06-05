CREATE TABLE IF NOT EXISTS inventory_movements (
    id BIGSERIAL PRIMARY KEY,

    branch_product_variant_id BIGINT NOT NULL REFERENCES branch_product_variants(id) ON DELETE CASCADE,

    type VARCHAR(32) NOT NULL CHECK (
        type IN (
            'STOCK_IN',
			'TRANSFER_IN',
			'TRANSFER_OUT',
			'SALE',
			'RETURN',
			'ADJUSTMENT_IN',
			'ADJUSTMENT_OUT',
			'DAMAGED',
			'EXPIRED'
        )
    ),

    quantity_change INTEGER NOT NULL,

    new_quantity INTEGER NOT NULL
        CHECK (new_quantity >= 0),

    notes TEXT,

    created_by BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP DEFAULT NOW()
)
