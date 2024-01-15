package com.songify.song.domain.service;

import com.songify.song.domain.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
@Log4j2
@Service
public class SongDeleter {
    private final SongRepository songRepository;

    public SongDeleter(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public void deleteSong(Long songId) {
        log.info("Deleting song...");
        songRepository.deleteById(songId);
    }
}
