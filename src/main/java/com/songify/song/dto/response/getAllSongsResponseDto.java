package com.songify.song.dto.response;

import com.songify.song.controller.Song;

import java.util.Map;

public record getAllSongsResponseDto(Map<Integer, Song> songs) {

}
