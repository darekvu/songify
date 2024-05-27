package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.SongDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class SongifyCrudFacade {
    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;
    private final ArtistAdder artistAdder;
    private final GenreAdder genreAdder;
    private final AlbumAdder albumAdder;

    public ArtistDto addArtist(ArtistRequestDto requestDto) {
        return artistAdder.addArtist(requestDto.name());
    }

    public AlbumDto addAlbumWithSong(AlbumRequestDto requestDto) {
        return albumAdder.addAlbum(requestDto.songId(),requestDto.title(),requestDto.releaseDate());
    }

    public GenreDto addGenre(GenreRequestDto genreDto) {
        return genreAdder.addGenre(genreDto.name());
    }

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
        songUpdater.updateById(songId, songValidatedAndReadyToUpdate);
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
