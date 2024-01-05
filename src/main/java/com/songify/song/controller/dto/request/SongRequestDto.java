package com.songify.song.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SongRequestDto(
        @NotNull(message = "SongName must not be null")
        @NotEmpty(message = "SongName must not be empty")
        String songName,
        @NotNull(message = "artist must not be null")
        @NotEmpty(message = "artist must not be empty")
        String artist
) {
}
