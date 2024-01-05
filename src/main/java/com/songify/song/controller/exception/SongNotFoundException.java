package com.songify.song.controller.exception;

public class SongNotFoundException extends RuntimeException {
    public SongNotFoundException(String message) {
        super(message);
    }
}
