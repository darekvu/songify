package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.infrastructure.crud.artist.ArtistRequestDto;
import com.songify.domain.crud.dto.ArtistWithAlbumAndSongsDto;
import com.songify.domain.crud.dto.SongDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class ArtistAdder {
    private final ArtistRepository artistRepository;

    ArtistDto addArtist(final String name) {
        Artist artist = new Artist(name);
        Artist savedArtist = artistRepository.save(artist);
        return new ArtistDto(savedArtist.getId(), savedArtist.getName());
    }

    ArtistWithAlbumAndSongsDto addArtistWithDefaultAlbumAndSong(ArtistRequestDto requestDto) {
        String name = requestDto.name();
        Artist savedArtist = saveArtistWithDefaultAlbumAndSong(name);
        Set<AlbumDto> albums = savedArtist.getAlbums()
                .stream()
                .map(album -> new AlbumDto(album.getId(), album.getTitle()))
                .collect(Collectors.toSet());

        Set<Song> songs = savedArtist.getAlbums()
                .stream()
                .flatMap(album -> album.getSongs().stream())
                .collect(Collectors.toSet());
        Set<SongDto> songDtos = songs.stream()
                .map(song -> new SongDto(song.getId(), song.getName(),new GenreDto(song.getGenre().getId(),song.getGenre().getName())))
                .collect(Collectors.toSet());
        return new ArtistWithAlbumAndSongsDto(savedArtist.getId(), savedArtist.getName(), albums, songDtos);
    }

    private Artist saveArtistWithDefaultAlbumAndSong(final String name) {
        Artist artist = new Artist(name);
        Album album = new Album();
        album.setTitle("default-album: " + UUID.randomUUID());
        album.setReleaseDate(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        Song song = new Song("default-song-name: " + UUID.randomUUID());
        album.addSongToAlbum(song);
        artist.addAlbum(album);
        return artistRepository.save(artist);
    }
}
