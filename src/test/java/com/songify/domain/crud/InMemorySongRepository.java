package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

 class InMemorySongRepository implements SongRepository {
    Map<Long, Song> db = new HashMap<>();
    AtomicLong index = new AtomicLong(0);

    @Override
    public List<Song> findAll(Pageable pageable) {
        return db.values().stream().toList();
    }

    @Override
    public Optional<Song> findById(Long songId) {
        return Optional.ofNullable(db.get(songId));
    }

    @Override
    public void deleteById(Long songId) {
        db.remove(songId);
    }

    @Override
    public void updateById(Long songId, Song newSong) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public Song save(Song song) {
        long index = this.index.getAndIncrement();
        db.put(index, song);
        song.setId(index);
        song.setGenre(new Genre(1L, "default"));
        return song;
    }

    @Override
    public int deleteByIdIn(Collection<Long> ids) {
        ids.forEach(id -> db.remove(id));
        return 0;
    }
}
