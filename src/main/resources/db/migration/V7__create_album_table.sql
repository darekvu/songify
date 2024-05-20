CREATE TABLE album
(
    id           BIGINT NOT NULL,
    title        VARCHAR(255),
    release_date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_album PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS album_id_seq START WITH 1 INCREMENT BY 1;