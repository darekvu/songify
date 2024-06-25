package com.songify.domain.crud;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

class InMemoryGenreRepository implements GenreRepository {
    Map<Long, Genre> db = new HashMap<>();
    AtomicLong index = new AtomicLong(1);
    public InMemoryGenreRepository(){
        save(new Genre(1L,"default"));
    }

    @Override
    public Genre save(final Genre genre) {
        long index = this.index.getAndIncrement();
        db.put(index,genre);
        genre.setId(index);
        return genre;
    }

    @Override
    public void deleteGenreById(Long id) {

    }

    @Override
    public Optional<Genre> findById(Long id) {
        Genre genre = db.get(id);
        return Optional.ofNullable(genre);
    }

    @Override
    public Set<Genre> findAll() {
        return new HashSet<>(db.values());
    }
}
