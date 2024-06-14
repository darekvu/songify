package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumDtoWithArtistsAndSongs;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistWithAlbumAndSongsDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import com.songify.infrastructure.crud.artist.ArtistRequestDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
    private final AlbumRetriever albumRetriever;
    private final ArtistRetriever artistRetriever;
    private final ArtistDeleter artistDeleter;
    private final ArtistAssigner artistAssigner;
    private final ArtistUpdater artistUpdater;

    public ArtistDto addArtist(ArtistRequestDto requestDto) {
        return artistAdder.addArtist(requestDto.name());
    }

    public void addArtistToAlbum(Long artistId, Long albumId) {
        artistAssigner.addArtistToAlbum(artistId, albumId);
    }

    public ArtistWithAlbumAndSongsDto addArtistsWithDefaultAlbumAndSong(ArtistRequestDto requestDto) {
        return artistAdder.addArtistWithDefaultAlbumAndSong(requestDto);
    }

    public AlbumDto addAlbumWithSong(AlbumRequestDto requestDto) {
        return albumAdder.addAlbum(requestDto.songId(), requestDto.title(), requestDto.releaseDate());
    }

    public ArtistDto updateArtistNameById(Long artistId, String newName) {
        return artistUpdater.updateArtistsNameById(artistId, newName);
    }

    public SongDto addSong(final SongRequestDto songRequestDto) {
        return songAdder.addSong(songRequestDto);
    }

    public GenreDto addGenre(GenreRequestDto genreDto) {
        return genreAdder.addGenre(genreDto.name());
    }

    public Set<ArtistDto> findAllArtists(Pageable pageable) {
        return artistRetriever.findAllArtists(pageable);
    }

    public List<SongDto> findAllSongs(final Pageable pageable) {
        return songRetriever.findAll(pageable);
    }

    public AlbumDtoWithArtistsAndSongs findAlbumByIdWithArtistsAndSongs(Long id) {
        return albumRetriever.findAlbumByIdWithArtistsAndSongs(id);
    }

    public void deleteArtistsByIdWithAlbumsAndSongs(Long artistId) {
        artistDeleter.deleteArtistByIdWithAlbumsAndSongs(artistId);
    }

    public SongDto findSongDtoById(final Long songId) {
        return songRetriever.findSongDtoById(songId);
    }

    public AlbumDto findAlbumDtoById(final Long albumId) {
        return albumRetriever.findAlbumDtoById(albumId);
    }

    public void deleteSongById(final Long songId) {
        songRetriever.existsById(songId);
        songDeleter.deleteSongById(songId);
    }

    public void deleteSongAndGenreById(final Long songId) {
        songDeleter.deleteSongAndGenreById(songId);
    }


    public void updateSongById(final Long songId, final SongDto newSongDto) {
        songRetriever.existsById(songId);
        Song songValidatedAndReadyToUpdate = new Song(newSongDto.name());
        //some domain validator
        songUpdater.updateById(songId, songValidatedAndReadyToUpdate);
    }

    public SongDto updateSongPartiallyById(final Long songId, final SongDto songFromRequest) {
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

    Set<AlbumDto> findAlbumsByArtistId(Long artistId) {
        return albumRetriever.findAlbumsDtoByArtistsId(artistId);
    }

}
