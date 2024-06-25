package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.SongCreateRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.CreateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PartiallyUpdatedSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.SongControllerDto;
import com.songify.infrastructure.crud.song.controller.dto.response.UpdateSongResponseDto;


import java.util.List;

 class SongControllerMapper {
     static SongDto mapFromCreateSongRequestDtoToSong(SongCreateRequestDto dto) {
        return SongDto.builder()
                .name(dto.songName())
                .build();
    }
     static SongDto mapFromUpdateSongRequestDtoToSongDto(UpdateSongRequestDto dto) {
         return SongDto
                 .builder()
                 .name(dto.songName())
                 .build();
     }
     static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(SongDto songDto){
         return new UpdateSongResponseDto(songDto.name(), "testt");
     }

     static CreateSongResponseDto mapFromSongToCreateSongResponseDto(SongDto song) {
        SongControllerDto songControllerDto = SongControllerMapper.mapFromSongDtoToSongControllerDto(song);
        CreateSongResponseDto body = new CreateSongResponseDto(songControllerDto);
        return body;
    }

     static SongDto mapFromPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateRequestDto dto) {
        return SongDto.builder()
                .name(dto.songName())
                .build();
    }

     static SongControllerDto mapFromSongDtoToSongControllerDto(SongDto song) {
        return new SongControllerDto(song.id(), song.name(),new GenreDto(song.genre().id(),song.genre().name()));
    }

     static GetAllSongsResponseDto mapFromToGetAllSongsResponseDto(List<SongDto> songs) {
        List<SongControllerDto> songControllerDtos = songs.stream()
                .map(songDto -> SongControllerDto
                        .builder()
                        .id(songDto.id())
                        .name(songDto.name())
                        .build())
                .toList();
        return new GetAllSongsResponseDto(songControllerDtos);
    }

     static PartiallyUpdatedSongResponseDto mapFromSongToPartiallyUpdatedSongResponseDto(SongDto song) {
        SongControllerDto songControllerDto = mapFromSongDtoToSongControllerDto(song);
        return new PartiallyUpdatedSongResponseDto(songControllerDto);

    }

     static GetSongResponseDto mapFromSongToGetSongResponseDto(SongDto song) {
        SongControllerDto songControllerDto = SongControllerDto.builder()
                .id(song.id())
                .name(song.name())
                .genre(new GenreDto(song.genre().id(),song.genre().name()))
                .build();
        return new GetSongResponseDto(songControllerDto);
    }

}
