package com.songify.song.domain.service;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.repository.SongRepository;
import com.songify.song.infrastructure.controller.exception.SongNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class SongRetriever {
    private final SongRepository songRepository;


    public List<Song> findAll(Pageable pageable) {
        log.info("retrieving all songs");
        return songRepository.findAll(pageable);
    }

    public Song findSongById(Long songId) {
        return songRepository.findById(songId).
                orElseThrow(() -> new SongNotFoundException("Song with id: " + songId + "not found"));
    }

    public void existsById(Long songId) {
       if (!songRepository.existsById(songId)){
           throw new SongNotFoundException("Song with id: " + songId + "not found");
        }
    }

    public Song findArtist(){
        return songRepository.findByArtistEqualsIgnoreCase("Ariana Grande")
                .orElseThrow(()->new SongNotFoundException("Not found huehue"));
    }
}
