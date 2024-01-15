package com.songify.song.infrastructure.controller;

import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateRequestDto;
import com.songify.song.infrastructure.controller.dto.request.SongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.createSongResponseDto;
import com.songify.song.domain.model.Song;

public class SongMapper {
    public static Song mapFromCreateSongRequestDtoToSong(SongRequestDto dto) {
        Song newSong = new Song(dto.songName(), dto.artist());
        return newSong;
    }

    public static createSongResponseDto mapFromSongToCreateSongResponseDto(Song newSong) {
        createSongResponseDto body = new createSongResponseDto(newSong);
        return body;
    }

    public static Song mapFromPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateRequestDto dto) {
        Song newSong = new Song(dto.songName(), dto.artist());
        return newSong;
    }


}
