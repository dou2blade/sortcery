CREATE TABLE IF NOT EXISTS user_branches (
    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE RESTRICT,
    branch_id BIGINT NOT NULL REFERENCES branches(id) ON DELETE RESTRICT,

    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),

    UNIQUE (user_id, branch_id)
);
