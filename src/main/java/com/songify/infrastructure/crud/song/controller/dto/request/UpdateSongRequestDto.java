package com.songify.infrastructure.crud.song.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateSongRequestDto(
        @NotNull(message = "songName must be provided")
        @NotEmpty(message = "songName must be  provided")
        String songName,
        @NotNull(message = "artist must be provided")
        @NotEmpty(message = "artist must be provided")
        String artist) {
}
