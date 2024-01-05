package com.songify.song.exception;

import org.springframework.http.HttpStatus;

public record ExceptionSongResponseDto(String message, HttpStatus status) {
}
