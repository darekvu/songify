package com.songify.song.controller.exception;

import org.springframework.http.HttpStatus;

public record ExceptionSongResponseDto(String message, HttpStatus status) {
}
