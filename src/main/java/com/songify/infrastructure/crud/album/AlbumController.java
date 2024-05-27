package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("album")
@AllArgsConstructor
@Log4j2
class AlbumController {
    private final SongifyCrudFacade songifyCrudFacade;

    @PostMapping
    ResponseEntity<AlbumDto> addAlbum(@RequestBody AlbumRequestDto requestDto) {
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(requestDto);
        return ResponseEntity.ok(albumDto);
    }
}
