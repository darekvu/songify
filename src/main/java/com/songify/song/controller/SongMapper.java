package com.songify.song.controller;

import com.songify.song.controller.dto.request.SongRequestDto;
import com.songify.song.controller.dto.response.createSongResponseDto;
import com.songify.song.model.Song;

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
