package com.songify.song.domain.service;

import com.songify.song.domain.repository.SongRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class SongDeleter {
    private final SongRepository songRepository;
    private final SongRetriever songRetriever;


    public void deleteSong(Long songId) {
        songRetriever.existsById(songId);
        log.info("Deleting song...");
        songRepository.deleteById(songId);
    }
}
