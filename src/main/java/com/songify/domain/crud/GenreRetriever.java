package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
class GenreRetriever {
    private final GenreRepository genreRepository;

    Genre findGenreById(Long id){
        return genreRepository
                .findById(id)
                .orElseThrow(()->new GenreNotFoundException("genre with id %s not found".formatted(id)));
    }

    Set<GenreDto> findAll(Pageable pageable) {
        return genreRepository.findAll().stream()
                .map(genre -> new GenreDto(genre.getId(),genre.getName()))
                .collect(Collectors.toSet());
    }
}
