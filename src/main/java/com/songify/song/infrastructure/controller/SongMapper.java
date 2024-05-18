package com.songify.song.infrastructure.controller;

import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateRequestDto;
import com.songify.song.infrastructure.controller.dto.request.SongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.domain.model.Song;

import java.util.List;

public class SongMapper {
    public static Song mapFromCreateSongRequestDtoToSong(SongRequestDto dto) {
        Song newSong = new Song(dto.songName(), dto.artist());
        return newSong;
    }

    public static CreateSongResponseDto mapFromSongToCreateSongResponseDto(Song song) {
        SongDto songDto = SongMapper.mapFromSongToSongDto(song);
        CreateSongResponseDto body = new CreateSongResponseDto(songDto);
        return body;
    }

    public static Song mapFromPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateRequestDto dto) {
        Song newSong = new Song(dto.songName(), dto.artist());
        return newSong;
    }

    public static SongDto mapFromSongToSongDto(Song song) {
        return new SongDto(song.getId(), song.getName(), song.getArtist());
    }

    public static GetAllSongsResponseDto mapFromToGetAllSongsResponseDto
            (List<Song> songs) {
        List<SongDto> songDtos = songs.stream()
                .map(SongMapper::mapFromSongToSongDto)
                .toList();
        return new GetAllSongsResponseDto(songDtos);
    }

    public static PartiallyUpdatedSongResponseDto mapFromSongToPartiallyUpdatedSongResponseDto(Song song) {
        SongDto songDto = mapFromSongToSongDto(song);
        return new PartiallyUpdatedSongResponseDto(songDto);

    }

    public static GetSongResponseDto mapFromSongToGetSongResponseDto(Song song) {
        SongDto songDto = SongMapper.mapFromSongToSongDto(song);
        return new GetSongResponseDto(songDto);
    }

}
