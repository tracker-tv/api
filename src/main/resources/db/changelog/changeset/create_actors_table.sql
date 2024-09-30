CREATE TABLE IF NOT EXISTS actors
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    creation_date    TIMESTAMP(0) NOT NULL,
    last_update_date TIMESTAMP(0) DEFAULT NULL
);
