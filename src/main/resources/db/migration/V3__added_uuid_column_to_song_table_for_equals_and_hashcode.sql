ALTER TABLE song
    ADD uuid UUID;

DROP SEQUENCE song_seq CASCADE;