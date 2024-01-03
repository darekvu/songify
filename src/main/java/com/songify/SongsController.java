package com.songify;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SongsController {
    Map<Integer, String> database = new HashMap<>();

    @GetMapping("/songs")
    public ResponseEntity<SongsResponseDto> getAllSongs() {
        database.put(1, "In the Name of Love");
        database.put(2, "MockingBird");
        database.put(3, "Despacito");
        SongsResponseDto songResponseDto = new SongsResponseDto(database);
        return ResponseEntity.ok(songResponseDto);
    }

    @GetMapping("/songs/{songId}")
    public ResponseEntity<SingleSongResponseDTO> getSongById(@PathVariable Integer songId) {
        String song = database.get(songId);
        if(song ==null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND).build();
        }
        SingleSongResponseDTO response = new SingleSongResponseDTO(song);
        return ResponseEntity.ok(response);
    }
}
