package com.songify.song.controller.dto.response;

import com.songify.song.model.Song;

public record PartiallyUpdatedSongResponseDto(Song updatedSong) {
}
