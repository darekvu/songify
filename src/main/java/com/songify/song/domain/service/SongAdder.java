package com.songify.song.domain.service;

import com.songify.song.domain.model.Song;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SongAdder {
    public void addSong(Song newSong) {
        log.info("adding new song "+ newSong);
        database.put(database.size() + 1, newSong);
    }
}
