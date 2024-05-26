package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.SongCreateRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.CreateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PartiallyUpdatedSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.UpdateSongResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.mapFromCreateSongRequestDtoToSong;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.mapFromPartiallyUpdateSongRequestDtoToSong;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.mapFromSongToCreateSongResponseDto;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.mapFromSongToGetSongResponseDto;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.mapFromSongToPartiallyUpdatedSongResponseDto;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.mapFromSongToUpdateSongResponseDto;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.mapFromToGetAllSongsResponseDto;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.mapFromUpdateSongRequestDtoToSongDto;

@RestController
@Log4j2
@RequestMapping("/songs")
@AllArgsConstructor
public class SongRestController {

    private final SongifyCrudFacade songFacade;

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@PageableDefault(page = 0, size = 30) Pageable pageable) {
        List<SongDto> allSongs = songFacade.findAll(pageable);
        GetAllSongsResponseDto response = mapFromToGetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{songId}")
    public ResponseEntity<GetSongResponseDto> getSongById(@PathVariable Long songId, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        SongDto song = songFacade.findSongDtoById(songId);
        GetSongResponseDto response = mapFromSongToGetSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateSongResponseDto> createSongRequestDto(@RequestBody @Valid SongCreateRequestDto request) {
        SongDto newSong = mapFromCreateSongRequestDtoToSong(request);
        SongDto savedSong = songFacade.addSong(newSong);
        CreateSongResponseDto body = mapFromSongToCreateSongResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSong(@PathVariable(name = "id") Long songId) {
        songFacade.deleteSong(songId);
        DeleteSongResponseDto response = new DeleteSongResponseDto("You deleted Song: " + " with id: " + songId, HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{songId}")
    public ResponseEntity<UpdateSongResponseDto> updateSong(@PathVariable Long songId, @RequestBody @Valid UpdateSongRequestDto request) {
        SongDto songDto = mapFromUpdateSongRequestDtoToSongDto(request);
        songFacade.updateById(songId, songDto);
        UpdateSongResponseDto body = mapFromSongToUpdateSongResponseDto(songDto);
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{songId}")
    public ResponseEntity<PartiallyUpdatedSongResponseDto> partiallyUpdateSong(
            @PathVariable Long songId,
            @RequestBody PartiallyUpdateRequestDto request
    ) {
        SongDto song = mapFromPartiallyUpdateSongRequestDtoToSong(request);
        SongDto updatedSong = songFacade.updatePartiallyById(songId, song);
        log.info("Partially updated song!");
        PartiallyUpdatedSongResponseDto body = mapFromSongToPartiallyUpdatedSongResponseDto(updatedSong);
        return ResponseEntity.ok(body);
    }


}
