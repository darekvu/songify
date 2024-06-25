package com.songify.infrastructure.crud.song.controller.dto.response;

import com.songify.domain.crud.dto.GenreDto;
import lombok.Builder;

@Builder
public record SongControllerDto(Long id, String name, GenreDto genre) {
}
