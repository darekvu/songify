package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfoDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.SongDto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

 class InMemoryAlbumRepository implements AlbumRepository {
    Map<Long, Album> db = new HashMap<>();
    AtomicLong index = new AtomicLong(0);

    @Override
    public Album save(Album album) {
        long index = this.index.getAndIncrement();
        db.put(index, album);
        album.setId(index);
        return album;
    }

    @Override
    public Optional<Album> findById(Long id) {
        Album album = db.get(id);
        return Optional.ofNullable(album);
    }

    @Override
    public Optional<AlbumInfoDto> findAlbumByIdWithSongsAndArtists(Long id) {
        Album album = db.get(id);
        Set<ArtistDto> artistsDto = album.getArtists()
                .stream()
                .map(artist -> new ArtistDto(artist.getId(), artist.getName()))
                .collect(Collectors.toSet());
        Set<SongDto> songsDto = album.getSongs()
                .stream()
                .map(song -> new SongDto(song.getId(), song.getName()))
                .collect(Collectors.toSet());

        return Optional.of(new AlbumInfoDto(album.getId(), artistsDto, songsDto));
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
        ids.forEach(id-> db.remove(id));
        return 0;
    }
}
