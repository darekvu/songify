package com.songify.song.infrastructure.controller;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.service.SongAdder;
import com.songify.song.domain.service.SongDeleter;
import com.songify.song.domain.service.SongRetriever;
import com.songify.song.domain.service.SongUpdater;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateRequestDto;
import com.songify.song.infrastructure.controller.dto.request.SongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.songify.song.infrastructure.controller.SongMapper.*;

@RestController
@Log4j2
@RequestMapping("/songs")
@AllArgsConstructor
public class SongRestController {

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@PageableDefault(page = 0,size = 30)Pageable pageable) {
        List<Song> allSongs = songRetriever.findAll(pageable);
//        log.info(songRetriever.findArtist());
        GetAllSongsResponseDto response = mapFromToGetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{songId}")
    public ResponseEntity<GetSongResponseDto> getSongById(@PathVariable Long songId, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        Song song = songRetriever.findSongById(songId);
        GetSongResponseDto response = mapFromSongToGetSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateSongResponseDto> createSongRequestDto(@RequestBody @Valid SongRequestDto request) {
        Song newSong = mapFromCreateSongRequestDtoToSong(request);
        Song savedSong = songAdder.addSong(newSong);
        CreateSongResponseDto body = mapFromSongToCreateSongResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSong(@PathVariable(name = "id") Long songId) {
        songDeleter.deleteSong(songId);
        DeleteSongResponseDto response = new DeleteSongResponseDto("You deleted Song: " + " with id: " + songId, HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{songId}")
    public ResponseEntity<UpdateSongResponseDto> updateSong(@PathVariable Long songId, @RequestBody @Valid UpdateSongRequestDto request) {
        String newSongName = request.songName();
        String newArtist = request.artist();
        Song newSong = new Song(newSongName, newArtist);
        songUpdater.updateById(songId, newSong);
        return ResponseEntity.ok(new UpdateSongResponseDto(newSong.getName(), newSong.getArtist()));
    }

    @PatchMapping("/{songId}")
    public ResponseEntity<PartiallyUpdatedSongResponseDto> partiallyUpdateSong(
            @PathVariable Long songId,
            @RequestBody PartiallyUpdateRequestDto request
    ) {
        Song song = mapFromPartiallyUpdateSongRequestDtoToSong(request);
        Song updatedSong = songUpdater.updatePartiallyById(songId, song);
        log.info("Partially updated song!");
        PartiallyUpdatedSongResponseDto body = mapFromSongToPartiallyUpdatedSongResponseDto(updatedSong);
        return ResponseEntity.ok(body);
    }


}
