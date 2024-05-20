package com.songify.domain.crud;

import com.songify.infrastructure.crud.song.controller.exception.SongNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongRetriever {
    private final SongRepository songRepository;

    List<Song> findAll(Pageable pageable) {
        log.info("retrieving all songs");
        return songRepository.findAll(pageable);
    }

    Song findSongById(Long songId) {
        return songRepository.findById(songId).
                orElseThrow(() -> new SongNotFoundException("Song with id: " + songId + "not found"));
    }

    void existsById(Long songId) {
        if (!songRepository.existsById(songId)) {
            throw new SongNotFoundException("Song with id: " + songId + "not found");
        }
    }

    Song findArtist() {
        return songRepository.findByArtistEqualsIgnoreCase("Ariana Grande")
                .orElseThrow(() -> new SongNotFoundException("Not found huehue"));
    }
}
