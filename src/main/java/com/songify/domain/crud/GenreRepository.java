package com.songify.domain.crud;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

interface GenreRepository extends Repository<Genre, Long> {
    Genre save(Genre genre);
    @Modifying
    @Query("DELETE FROM Genre g WHERE g.id = :id")
    void deleteGenreById(Long id);

    Optional<Genre> findGenreById(Long id);

    Set<Genre> findAll();
}
