package com.songify.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Transactional
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongUpdater {
    private final SongRepository songRepository;
    private final SongRetriever songRetriever;

    void updateById(Long songId, Song newSong) {
        songRetriever.existsById(songId);
        songRepository.updateById(songId, newSong);
    }

    Song updatePartiallyById(Long songId, Song songFromRequest) {
        Song songFromDatabase = songRetriever.findSongById(songId);
        Song.SongBuilder builder = Song.builder();
        if (songFromRequest.getName() != null) {
            builder.name(songFromRequest.getName());
            log.info("partially updated song name");
        } else {
            builder.name(songFromDatabase.getName());
        }

//        if (songFromRequest.getArtist() != null) {
//            builder.artist(songFromRequest.getArtist());
//            log.info("partially updated artist");
//        } else {
//            builder.artist(songFromDatabase.getArtist());
//        }
        Song toSave = builder.build();
        updateById(songId, toSave);
        return toSave;
    }
}
