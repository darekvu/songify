package com.songify.song.model;

import lombok.Builder;

@Builder
public record Song(String name, String artist) {
}
