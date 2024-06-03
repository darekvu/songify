package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.infrastructure.crud.artist.ArtistRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SongifyCrudFacadeTest {
    SongifyCrudFacade songifyCrudFacade = SongifyCrudFacadeConfiguration.createSongifyCrud(
            new InMemorySongRepository(),
            new InMemoryGenreRepository(),
            new InMemoryArtistRepository(),
            new InMemoryAlbumRepository()
    );


    @Test
    @DisplayName("Should add artist 'shawn mendes' with id:0 when artist request was sent")
    void should_add_artist_with_id_zero_when_request_was_sent() {
//        given
        ArtistRequestDto artistRequestDto = ArtistRequestDto.builder()
                .name("Shawn Mendes")
                .build();
//        when
        ArtistDto actual = songifyCrudFacade.addArtist(artistRequestDto);
        System.out.println(actual);
        //then
        assertThat(actual.id()).isEqualTo(0);
        assertThat(actual.name()).isEqualTo(artistRequestDto.name());
    }

    @Test
    void should_add_artist_and_return_correct_dto() {
//        given
        ArtistRequestDto artistRequestDto = ArtistRequestDto.builder()
                .name("Shawn Mendes")
                .build();
//        when
        ArtistDto response = songifyCrudFacade.addArtist(artistRequestDto);
        System.out.println(response);
        //then
        assertThat(response.name()).isNotNull();
        assertThat(response.id()).isNotNull();
    }

    @Test
    @DisplayName("Should throw exception ArtistNotFound when id:1")
    void should_throw_exception_when_artist_not_found() {
        //given
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged()).isEmpty());
        //when
        //then
        assertThatThrownBy(() -> songifyCrudFacade.deleteArtistsByIdWithAlbumsAndSongs(1L))
                .isInstanceOf(ArtistNotFoundException.class)
                .hasMessageContaining("artist with [%s] not found".formatted(1L));
    }

    @Test
    @DisplayName("Should not throw exception ArtistNotFound when id:1")
    void should_not_throw_exception_when_artist_not_found() {
//        given
        ArtistRequestDto artistRequestDto = ArtistRequestDto.builder()
                .name("Shawn Mendes")
                .build();
        //when
        ArtistDto artistDto = songifyCrudFacade.addArtist(artistRequestDto);
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isNotEmpty();
        //then
        songifyCrudFacade.deleteArtistsByIdWithAlbumsAndSongs(artistDto.id());

        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
    }

    @Test
    @DisplayName("Should delete artist by id when he has no albums")
    void should_delete_artist_by_id_when_he_has_no_albums() {
//        given
        ArtistRequestDto artistRequestDto = ArtistRequestDto.builder()
                .name("Shawn Mendes")
                .build();
        //when
        ArtistDto artistDto = songifyCrudFacade.addArtist(artistRequestDto);
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isNotEmpty();
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistDto.id())).isEmpty();
        //then
        songifyCrudFacade.deleteArtistsByIdWithAlbumsAndSongs(artistDto.id());

        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
    }

//    public void should_delee
}