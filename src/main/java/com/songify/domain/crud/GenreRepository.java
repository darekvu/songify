package com.songify.domain.crud;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

interface GenreRepository extends Repository<Genre, Long> {
    Genre save(Genre genre);
    @Modifying
    @Query("DELETE FROM Genre g WHERE g.id = :id")
    void deleteGenreById(Long id);
}
