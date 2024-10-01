CREATE TABLE IF NOT EXISTS actors
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    birthday         DATE DEFAULT NULL,
    deathday         DATE DEFAULT NULL,
    description      TEXT         DEFAULT NULL,
    creation_date    TIMESTAMP(0) NOT NULL,
    last_update_date TIMESTAMP(0) DEFAULT NULL
);
