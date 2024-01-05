package com.songify.song.controller;

import com.songify.song.dto.request.SongRequestDto;
import com.songify.song.dto.response.createSongResponseDto;

public class SongMapper {
    public static Song mapFromCreateSongRequestDtoToSong(SongRequestDto dto) {
        Song newSong = new Song(dto.songName(), dto.artist());
        return newSong;
    }

    public static createSongResponseDto mapFromSongToCreateSongResponseDto(Song newSong) {
        createSongResponseDto body = new createSongResponseDto(newSong);
        return body;
    }
}
