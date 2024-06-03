package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
class ArtistDeleter {
    private final SongDeleter songDeleter;
    ArtistRepository artistRepository;
    ArtistRetriever artistRetriever;
    AlbumDeleter albumDeleter;
    AlbumRetriever albumRetriever;

    void deleteArtistByIdWithAlbumsAndSongs(final Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException("artist with [%s] not found".formatted(artistId)));
        Set<Album> artistsAlbums = albumRetriever.findAlbumsByArtistsId(artist.getId());
        if (artistsAlbums.isEmpty()) {
            artistRepository.deleteById(artistId);
            return;
        }
        artistsAlbums.stream()
                .filter(album -> album.getArtists().size() >= 2)
                .forEach(album -> album.removeArtist(artist));

        Set<Album> albumsWithOnlyOneArtist = artistsAlbums.stream()
                .filter(album -> album.getArtists().size() == 1)
                .collect(Collectors.toSet());

        Set<Long> allSongsIdsFromAllAlbumsWhereWasOnlyThisArtist = albumsWithOnlyOneArtist.stream()
                .flatMap(album -> album.getSongs().stream())
                .map(Song::getId)
                .collect(Collectors.toSet());
        songDeleter.deleteAllSongsByIds(allSongsIdsFromAllAlbumsWhereWasOnlyThisArtist);

        Set<Long> albumIdsToDelete = albumsWithOnlyOneArtist.stream()
                .map(album -> album.getId())
                .collect(Collectors.toSet());
        albumDeleter.deleteAllAlbumsByIds(albumIdsToDelete);
        artistRepository.deleteById(artistId);
    }
}
