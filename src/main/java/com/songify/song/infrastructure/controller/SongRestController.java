package com.songify.song.infrastructure.controller;

import com.songify.song.domain.service.SongAdder;
import com.songify.song.domain.service.SongRetriever;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateRequestDto;
import com.songify.song.infrastructure.controller.dto.request.SongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.infrastructure.controller.exception.SongNotFoundException;
import com.songify.song.domain.model.Song;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/songs")
public class SongRestController {

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;

    public SongRestController(SongAdder songAdder, SongRetriever songRetriever) {
        this.songAdder = songAdder;
        this.songRetriever = songRetriever;
    }

    @GetMapping("/")
    public ResponseEntity<getAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            Map<Integer, Song> songs = songRetriever.findAllLimitedBy(limit);
            getAllSongsResponseDto response = new getAllSongsResponseDto(songs);
            return ResponseEntity.ok(response);
        }
        getAllSongsResponseDto songResponseDto = new getAllSongsResponseDto(songRetriever.findAll());
        return ResponseEntity.ok(songResponseDto);
    }

    @GetMapping("/{songId}")
    public ResponseEntity<GetSongResponseDto> getSongById(@PathVariable Integer songId, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        Song song = songRetriever.findAll().get(songId);

        if (!songRetriever.findAll().containsKey(songId)) {
            throw new SongNotFoundException("Song with: " + songId + " not found");
        }
        GetSongResponseDto response = new GetSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<createSongResponseDto> createSongRequestDto(@RequestBody @Valid SongRequestDto request) {
        Song newSong = SongMapper.mapFromCreateSongRequestDtoToSong(request);
        songAdder.addSong(newSong);
        createSongResponseDto body = SongMapper.mapFromSongToCreateSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSong(@PathVariable(name = "id") Integer songId) {
        if (!songRetriever.findAll().containsKey(songId)) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DeleteSongResponseDto("Song with this id not found", HttpStatus.NOT_FOUND));
            throw new SongNotFoundException("Song with id" + songId + "Not found");

        }

        Song songName = songRetriever.findAll().get(songId);
        songRetriever.findAll().remove(songId);
        DeleteSongResponseDto response = new DeleteSongResponseDto("You deleted Song: " + songName + " with id: " + songId, HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{songId}")
    public ResponseEntity<UpdateSongResponseDto> updateSong(@PathVariable Integer songId, @RequestBody UpdateSongRequestDto request) {
        if (!songRetriever.findAll().containsKey(songId)) {
            throw new SongNotFoundException("Song with id" + songId + "Not found");
        }
        String newSongName = request.songName();
        String newArtist = request.artist();
        Song newSong = new Song(newSongName, newArtist);
        Song oldSong = songRetriever.findAll().put(songId, newSong);

        log.info("Updated Song With id: " + songId + " with song name " + oldSong.name() + "to -->: " + newSongName + "and artist: " + oldSong.artist() + " " + newArtist);
        return ResponseEntity.ok(new UpdateSongResponseDto(newSong.name(), newSong.artist()));
    }

    @PatchMapping("/{songId}")
    public ResponseEntity<PartiallyUpdatedSongResponseDto> partiallyUpdateSong(
            @PathVariable Integer songId,
            @RequestBody PartiallyUpdateRequestDto request
    ) {
        if (!songRetriever.findAll().containsKey(songId)) {
            throw new SongNotFoundException("Song with id" + songId + "Not found");
        }
        Song oldSong = songRetriever.findAll().get(songId);
        Song.SongBuilder builder = Song.builder();
        if (request.songName() != null) {
            builder.name(request.songName());
            log.info("partially updated song name");
        } else {
            builder.name(oldSong.name());
        }

        if (request.artist() != null) {
            builder.artist(request.artist());
            log.info("partially updated artist");
        } else {
            builder.artist(oldSong.artist());
        }
        Song updatedSong = builder.build();
        songRetriever.findAll().put(songId, updatedSong);
        log.info("Partially updated song!");
        return ResponseEntity.ok(new PartiallyUpdatedSongResponseDto(updatedSong));
    }


}
