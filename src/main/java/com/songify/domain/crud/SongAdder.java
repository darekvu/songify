package com.songify.domain.crud.song;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Transactional
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongAdder {
    private final SongRepository songRepository;

    Song addSong(Song newSong) {
        log.info("adding new song " + newSong);
        return songRepository.save(newSong);
    }
}
