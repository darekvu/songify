package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
@Transactional
class AlbumAdder {
    private final AlbumRepository albumRepository;
    private final SongRetriever songRetriever;

    AlbumDto addAlbum(final Long songId, final String title, final Instant instant) {
        Song songById = songRetriever.findSongById(songId);
        Album album = new Album();
        album.setTitle(title);
        album.addSongToAlbum(songById);
        album.setReleaseDate(instant);
        Album savedAlbum = albumRepository.save(album);
        return new AlbumDto(savedAlbum.getId(),savedAlbum.getTitle());
    }
}
