package com.songify.song.domain.repository;

import com.songify.song.domain.model.Song;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class SongRepositoryInMemory implements SongRepository {

    Map<Integer, Song> database = new HashMap<>(
            Map.of(
                    1, new Song("In the Name of Love", "Shawn Mendes"),
                    2, new Song("Calm Down", "Selena Gomez"),
                    3, new Song("Despacito", "Louis Fonsi"),
                    4, new Song("MockingBird", "Eminem")
            )
    );
    @Override
     public Song save(Song newSong) {
        database.put(database.size() + 1, newSong);
        return newSong;
    }
    @Override
    public List<Song> findAll(){
         return database.values().stream().toList();
    }
}
