package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class InMemorySongRepository implements SongRepository {
    @Override
    public List<Song> findAll(Pageable pageable) {
        return List.of();
    }

    @Override
    public Optional<Song> findById(Long songId) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long songId) {

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
        return null;
    }

    @Override
    public int deleteByIdIn(Collection<Long> ids) {
        return 0;
    }
}
