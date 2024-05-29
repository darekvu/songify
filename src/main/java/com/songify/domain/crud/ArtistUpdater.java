package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
class ArtistUpdater {
    private final ArtistRetriever artistRetriever;
    private final ArtistRepository artistRepository;


    ArtistDto updateArtistsNameById(Long artistId, String newName) {
        artistRepository.updateNameById(newName, artistId);
        Artist artist = artistRetriever.findById(artistId);
        return new ArtistDto(artist.getId(),newName);
    }
}
