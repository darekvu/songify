package com.songify.domain.crud.song;

import com.songify.domain.crud.song.dto.SongDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SongCrudFacade {
    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;

    public List<SongDto> findAll(final Pageable pageable) {
        return songRetriever.findAll(pageable)
                .stream()
                .map(SongDomainMapper::mapFromSongToSongDto)
                .toList();
    }

    public SongDto findSongDtoById(final Long songId) {
        final Song song = songRetriever.findSongById(songId);
        return SongDto.builder()
                .id(song.getId())
                .name(song.getName())
                .build();
    }

    public void deleteSong(final Long songId) {
        songRetriever.existsById(songId);
        songDeleter.deleteById(songId);
    }

    public void updateById(final Long songId, final SongDto newSongDto) {
        songRetriever.existsById(songId);
        Song songValidatedAndReadyToUpdate = new Song(newSongDto.name());
        //some domain validator
        songUpdater.updateById(songId,songValidatedAndReadyToUpdate);
    }

    public SongDto updatePartiallyById(final Long songId, final SongDto songFromRequest) {
        songRetriever.existsById(songId);
        Song songFromDatabase = songRetriever.findSongById(songId);
        Song toSave = new Song();
        if (songFromRequest.name() != null) {
            toSave.setName(songFromRequest.name());
        } else {
            toSave.setName(songFromDatabase.getName());
        }
        songUpdater.updatePartiallyById(songId, toSave);
        return SongDto.builder()
                .id(toSave.getId())
                .name(toSave.getName())
                .build();
    }

    public SongDto addSong(final SongDto songDto) {
//        some domain validator
        String name = songDto.name();
//        some domain validator ended checking
        Song song = new Song(songDto.name());
        Song addedSong = songAdder.addSong(song);
        return SongDto.builder()
                .id(addedSong.getId())
                .name(addedSong.getName())
                .build();
    }
}
