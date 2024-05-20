package com.songify.infrastructure.crud.song.controller.exception;

import org.springframework.http.HttpStatus;

public record ExceptionSongResponseDto(String message, HttpStatus status) {
}
