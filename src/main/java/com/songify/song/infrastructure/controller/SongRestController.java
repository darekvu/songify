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
import com.songify.song.infrastructure.controller.exception.SongNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/songs")
@AllArgsConstructor
public class SongRestController {

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;

    @GetMapping("/")
    public ResponseEntity<getAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            List<Song> songs = songRetriever.findAllLimitedBy(limit);
            getAllSongsResponseDto response = new getAllSongsResponseDto(songs);
            return ResponseEntity.ok(response);
        }
        getAllSongsResponseDto songResponseDto = new getAllSongsResponseDto(songRetriever.findAll());
        return ResponseEntity.ok(songResponseDto);
    }

    @GetMapping("/{songId}")
    public ResponseEntity<GetSongResponseDto> getSongById(@PathVariable Long songId, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        Song song = songRetriever.findSongById(songId);

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
//        if (!songRetriever.findAll().contains(songId)) {
//            throw new SongNotFoundException("Song with id" + songId + "Not found");
//        }
        Song song = SongMapper.mapFromPartiallyUpdateSongRequestDtoToSong(request);
        Song updatedSong = songUpdater.updatePartiallyById(songId, song);
        log.info("Partially updated song!");
        return ResponseEntity.ok(new PartiallyUpdatedSongResponseDto(updatedSong));
    }


}
