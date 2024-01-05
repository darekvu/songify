package com.songify.song.controller;

import com.songify.song.dto.request.SongRequestDto;
import com.songify.song.dto.request.UpdateRequestDto;
import com.songify.song.dto.response.DeleteSongResponseDto;
import com.songify.song.dto.response.SingleSongResponseDto;
import com.songify.song.dto.response.SongsResponseDto;
import com.songify.song.dto.response.UpdateSongResponseDto;
import com.songify.song.exception.SongNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class SongRestController {

    Map<Integer, Song> database = new HashMap<>(
            Map.of(
                    1, new Song("In the Name of Love", "Shawn Mendes"),
                    2, new Song("Calm Down", "Selena Gomez"),
                    3, new Song("Despacito", "Louis Fonsi"),
                    4, new Song("MockingBird", "Eminem")
            )
    );

    @GetMapping("/songs")
    public ResponseEntity<SongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            Map<Integer, Song> songs = database.entrySet()
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            SongsResponseDto response = new SongsResponseDto(songs);
            return ResponseEntity.ok(response);
        }
        SongsResponseDto songResponseDto = new SongsResponseDto(database);
        return ResponseEntity.ok(songResponseDto);
    }

    @GetMapping("/songs/{songId}")
    public ResponseEntity<SingleSongResponseDto> getSongById(@PathVariable Integer songId, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        Song song = database.get(songId);

        if (!database.containsKey(songId)) {
            throw new SongNotFoundException("Song with: " + songId + " not found");
        }
        SingleSongResponseDto response = new SingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/songs")
    public ResponseEntity<SingleSongResponseDto> postSong(@RequestBody @Valid SongRequestDto request) {
        Song newSong = new Song(request.songName(), request.artist());
        database.put(database.size() + 1, newSong);
        return ResponseEntity.ok(new SingleSongResponseDto(newSong));
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSong(@PathVariable(name = "id") Integer songId) {
        if (!database.containsKey(songId)) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DeleteSongResponseDto("Song with this id not found", HttpStatus.NOT_FOUND));
            throw new SongNotFoundException("Song with id" + songId + "Not found");

        }

        Song songName = database.get(songId);
        database.remove(songId);
        DeleteSongResponseDto response = new DeleteSongResponseDto("You deleted Song: " + songName + " with id: " + songId, HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/songs/{songId}")
    public ResponseEntity<UpdateSongResponseDto> updateSong(@PathVariable Integer songId, @RequestBody UpdateRequestDto request) {
        if (!database.containsKey(songId)) {
            throw new SongNotFoundException("Song with id" + songId + "Not found");
        }
        String newSongName = request.songName();
        String newArtist = request.artist();
        Song newSong = new Song(newSongName, newArtist);
        Song oldSong = database.put(songId, newSong);

        log.info("Updated Song With id: " + songId + " with song name " + oldSong.name() + "to -->: " + newSongName + "and artist: "+oldSong.artist()+" "+newArtist);
        return ResponseEntity.ok(new UpdateSongResponseDto(newSong.name(),newSong.artist()));

    }


}
