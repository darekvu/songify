package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumDtoWithArtistsAndSongs;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.SongDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class AlbumRetriever {
    private final AlbumRepository albumRepository;

    AlbumDtoWithArtistsAndSongs findAlbumByIdWithArtistsAndSongs(final Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id [%s] not found".formatted(id)));
        AlbumDto albumDto = new AlbumDto(album.getId(), album.getTitle());
        Set<Artist> artists = album.getArtists();
        Set<Song> songs = album.getSongs();

        Set<ArtistDto> artistsDto = artists.stream()
                .map(artist -> new ArtistDto(artist.getId(), artist.getName())).collect(Collectors.toSet());

        Set<SongDto> songsDto = songs.stream().map(song -> new SongDto(song.getId(), song.getName())).collect(Collectors.toSet());
        return new AlbumDtoWithArtistsAndSongs(albumDto, artistsDto, songsDto);
    }
}
