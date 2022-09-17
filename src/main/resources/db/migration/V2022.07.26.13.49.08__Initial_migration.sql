CREATE TABLE IF NOT EXISTS images (
    id BIGSERIAL,
    name VARCHAR(255),
    time TIMESTAMP,
    key VARCHAR(255),
    size INTEGER
);