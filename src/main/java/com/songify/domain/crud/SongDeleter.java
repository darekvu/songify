package com.songify.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Transactional
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongDeleter {
    private final SongRepository songRepository;
    private final SongRetriever songRetriever;
    private final GenreDeleter genreDeleter;

    void deleteSongById(Long songId) {
        songRetriever.existsById(songId);
        songRepository.deleteById(songId);
    }

    void deleteSongAndGenreById(Long songId) {
        Song song = songRetriever.findSongById(songId);
        Long genreId = song.getGenre().getId();
        deleteSongById(songId);
        genreDeleter.deleteGenreById(genreId);
    }
}
