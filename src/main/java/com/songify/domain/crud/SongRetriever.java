package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.song.controller.exception.SongNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
@Service
class SongRetriever {
    private final SongRepository songRepository;

    List<SongDto> findAll(Pageable pageable) {
        log.info("retrieving all songs");
        return songRepository.findAll(pageable)
                .stream()
                .map(SongDomainMapper::mapFromSongToSongDto)
                .toList();
    }

    SongDto findSongDtoById(Long songId) {
        return songRepository.findById(songId)
                .map(song -> SongDto.builder()
                        .id(song.getId())
                        .name(song.getName())
                        .build())
                .orElseThrow(() -> new SongNotFoundException("Song with id: " + songId + "not found"));
    }

    Song findSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("Song with id: " + id + "not found"));
    }

    void existsById(Long songId) {
        if (!songRepository.existsById(songId)) {
            throw new SongNotFoundException("Song with id: " + songId + "not found");
        }
    }

}
