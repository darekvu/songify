package com.songify.infrastructure.crud.genre;

import com.songify.domain.crud.dto.GenreDto;

import java.util.Set;

record GetAllGenresResponseDto(Set<GenreDto> genres) {
}
