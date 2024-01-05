package com.songify.song.controller.dto.response;

import com.songify.song.model.Song;

import java.util.Map;

public record getAllSongsResponseDto(Map<Integer, Song> songs) {

}
