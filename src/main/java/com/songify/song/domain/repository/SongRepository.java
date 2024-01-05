package com.songify.song.domain.repository;

import com.songify.song.domain.model.Song;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SongRepository {

    Map<Integer, Song> database = new HashMap<>(
            Map.of(
                    1, new Song("In the Name of Love", "Shawn Mendes"),
                    2, new Song("Calm Down", "Selena Gomez"),
                    3, new Song("Despacito", "Louis Fonsi"),
                    4, new Song("MockingBird", "Eminem")
            )
    );

     public Song saveToDatabase(Song newSong) {
        database.put(database.size() + 1, newSong);
        return newSong;
    }

    public Map<Integer,Song> findAll(){
         return database;
    }
}
