CREATE TABLE song
(
    id           BIGSERIAL PRIMARY KEY       NOT NULL,
    name         VARCHAR(255) NOT NULL,
    release_date TIMESTAMP WITHOUT TIME ZONE,
    language     VARCHAR(255),
    duration     BIGINT
);