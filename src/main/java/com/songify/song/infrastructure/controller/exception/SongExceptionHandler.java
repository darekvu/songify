package com.songify.song.infrastructure.controller.exception;

import com.songify.song.infrastructure.controller.SongRestController;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice(assignableTypes = SongRestController.class)
@Log4j2
public class SongExceptionHandler {
    @ExceptionHandler(SongNotFoundException.class)
    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    public ExceptionSongResponseDto handleException(SongNotFoundException exception) {
        log.warn("error while accessing song");
        return new ExceptionSongResponseDto(exception.getMessage(), NOT_FOUND);
    }
}
