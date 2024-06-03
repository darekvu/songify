package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryArtistRepository implements ArtistRepository {
    Map<Long, Artist> db = new HashMap<>();
    AtomicLong index = new AtomicLong();

    @Override
    public Artist save(Artist artist) {
        long index = this.index.getAndIncrement();
        db.put(index, artist);
        artist.setId(index);
        return artist;
    }

    @Override
    public Set<Artist> findAll(Pageable pageable) {
        return new HashSet<>(db.values());
    }

    @Override
    public Optional<Artist> findById(Long id) {
        Artist artist = db.get(id);
        return Optional.ofNullable(artist);
    }

    @Override
    public int deleteById(Long id) {
        db.remove(id);
        return id.intValue();
    }

    @Override
    public int updateNameById(String name, Long id) {
        return 0;
    }
}
