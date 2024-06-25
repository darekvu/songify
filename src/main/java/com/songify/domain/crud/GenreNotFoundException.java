package com.songify.domain.crud;

class GenreNotFoundException extends RuntimeException{
    GenreNotFoundException(String message) {
        super(message);
    }
}
