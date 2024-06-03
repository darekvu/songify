package com.songify.domain.crud;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryAlbumRepository implements AlbumRepository {
    Map<Long, Album> db = new HashMap<>();

    @Override
    public Album save(Album album) {
        return null;
    }

    @Override
    public Optional<Album> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Album> findAlbumByIdWithSongsAndArtists(Long id) {
        return Optional.empty();
    }

    @Override
    public Set<Album> findAllAlbumsByArtistsId(Long id) {
        return db.values()
                .stream()
                .filter(album -> album.getArtists().stream()
                        .anyMatch(artist -> artist.getId().equals(id))).collect(Collectors.toSet());
    }

    @Override
    public int deleteByIdIn(Collection<Long> ids) {
        return 0;
    }
}
