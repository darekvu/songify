package com.songify.infrastructure.crud.genre;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("genre")
@AllArgsConstructor
class GenreController {
    private final SongifyCrudFacade songifyCrudFacade;


    @PostMapping
    ResponseEntity<GenreDto> postArtist(@RequestBody GenreRequestDto genreRequestDto) {
        GenreDto genreDto = songifyCrudFacade.addGenre(genreRequestDto);
        return ResponseEntity.ok(genreDto);
    }
    @GetMapping
    ResponseEntity<GetAllGenresResponseDto> getGenres(@PageableDefault(size = 10,page = 0) Pageable pageable){
        Set<GenreDto> allGenres = songifyCrudFacade.findAllGenres(pageable);
        return ResponseEntity.ok(new GetAllGenresResponseDto(allGenres));
    }
}
