package com.songify.infrastructure.crud.artist;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("artists")
class ArtistController {
    private final SongifyCrudFacade songifyCrudFacade;

    @PostMapping
    ResponseEntity<ArtistDto> postArtist(@RequestBody ArtistRequestDto artistRequestDto) {
        ArtistDto artistDto = songifyCrudFacade.addArtist(artistRequestDto);
        return ResponseEntity.ok(artistDto);
    }

    @GetMapping
    ResponseEntity<AllArtistsDto> getArtists(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(pageable);
        return ResponseEntity.ok(new AllArtistsDto(allArtists));
    }

    @DeleteMapping("/{artistId}")
    ResponseEntity<String> deleteArtists(@PathVariable Long artistId) {
        songifyCrudFacade.deleteArtistsByIdWithAlbumsAndSongs(artistId);
        return ResponseEntity.ok("Deleted successfully ");
    }

    @PutMapping("/{artistId}/{albumId}")
    ResponseEntity<String> deleteArtists(@PathVariable Long artistId, @PathVariable Long albumId) {
        songifyCrudFacade.addArtistToAlbum(artistId, albumId);
        return ResponseEntity.ok("Assigned artist to album ");
    }
}
