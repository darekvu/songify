package com.songify.domain.crud.song;

import com.songify.domain.crud.song.dto.SongDto;

class SongDomainMapper {
    public static SongDto mapFromSongToSongDto(Song song) {
        return SongDto.builder()
                .id(song.getId())
                .name(song.getName())
                .build();
    }
}
