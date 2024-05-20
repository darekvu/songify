CREATE TABLE album
(
    id           BIGSERIAL PRIMARY KEY,
    title        VARCHAR(255),
    release_date TIMESTAMP(6) WITH TIME ZONE
);

CREATE SEQUENCE IF NOT EXISTS album_id_seq START WITH 1 INCREMENT BY 1;