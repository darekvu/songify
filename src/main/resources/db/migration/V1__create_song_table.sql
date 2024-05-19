CREATE SEQUENCE IF NOT EXISTS song_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE song
(
    id           BIGSERIAL PRIMARY KEY       NOT NULL,
    name         VARCHAR(255) NOT NULL,
    artist       VARCHAR(255) NOT NULL,
    release_date TIMESTAMP WITHOUT TIME ZONE,
    language     VARCHAR(255),
    duration     BIGINT
);