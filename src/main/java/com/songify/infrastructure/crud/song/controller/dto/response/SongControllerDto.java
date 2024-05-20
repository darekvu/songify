package com.songify.infrastructure.crud.song.controller.dto.response;

import lombok.Builder;

@Builder
public record SongControllerDto(Long id, String name) {
}
