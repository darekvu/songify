package com.songify.song;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SongRequestDto(
        @NotNull(message = "SongName must not be null")
        @NotEmpty(message = "SongName must not be empty")
        String songName
) {
}
