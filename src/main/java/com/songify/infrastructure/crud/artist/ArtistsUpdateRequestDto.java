package com.songify.infrastructure.crud.artist;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

record ArtistsUpdateRequestDto(
        @NotNull(message = "new artist name must not be null")
        @NotEmpty(message = "new artist name must not be empty")
        String name) {
}
