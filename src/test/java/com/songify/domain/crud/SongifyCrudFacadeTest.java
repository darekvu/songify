package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import com.songify.infrastructure.crud.artist.ArtistRequestDto;
import com.songify.infrastructure.crud.song.controller.exception.SongNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

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

    @Test
    @DisplayName("Should delete artist by id when he has 1 album")
    void should_delete_artist_with_album_and_songs_when_there_was_the_only_artist_in_album() {
//        given
        ArtistRequestDto artistRequestDto = ArtistRequestDto.builder()
                .name("Shawn Mendes")
                .build();
        //when
        ArtistDto artistDto = songifyCrudFacade.addArtist(artistRequestDto);
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(songRequestDto);

        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto.builder()
                .songId(songDto.id())
                .title("album title 1")
                .build());
        songifyCrudFacade.addArtistToAlbum(artistDto.id(), albumDto.id());
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistDto.id())).size().isEqualTo(1);
        assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id()).artists().size()).isEqualTo(1);
        //then
        songifyCrudFacade.deleteArtistsByIdWithAlbumsAndSongs(artistDto.id());

        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findSongDtoById(songDto.id()));
        assertThat(throwable).isInstanceOf(SongNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Song with id: 0not found");
        assertThatThrownBy(() -> songifyCrudFacade.findAlbumDtoById(albumDto.id()))
                .isInstanceOf(AlbumNotFoundException.class)
                .hasMessageContaining("Album with [%s] not found".formatted(albumDto.id()));
    }

    @Test
    void should_delete_only_artist_from_album_by_id_when_there_were_more_than_one_artist() {
//        given
        ArtistRequestDto artistRequestDto = ArtistRequestDto.builder()
                .name("Shawn Mendes")
                .build();
        //when
        ArtistDto artistDto = songifyCrudFacade.addArtist(artistRequestDto);
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(songRequestDto);

        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto.builder()
                .songId(songDto.id())
                .title("album title 1")
                .build());
        songifyCrudFacade.addArtistToAlbum(artistDto.id(), albumDto.id());
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistDto.id())).size().isEqualTo(1);
        assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id()).artists().size()).isEqualTo(1);
        //then
        songifyCrudFacade.deleteArtistsByIdWithAlbumsAndSongs(artistDto.id());

        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
    }

    @Test
    void should_add_album_with_song() {
        //given

        //when

        //then
    }

    @Test
    void should_add_song() {
        //given
        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();
        //when
        SongDto songDto = songifyCrudFacade.addSong(song);
        //then
        List<SongDto> allSongs = songifyCrudFacade.findAllSongs(Pageable.unpaged());
        assertThat(songDto.name()).isEqualTo(song.name());
        assertThat(allSongs).extracting(SongDto::id)
                .containsExactly(0L);
    }

    @Test
    void should_add_artist_to_album() {
        //given
        ArtistRequestDto artistRequestDto = ArtistRequestDto.builder()
                .name("Shawn Mendes")
                .build();
        //when
        ArtistDto artistDto = songifyCrudFacade.addArtist(artistRequestDto);
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(songRequestDto);

        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto.builder()
                .songId(songDto.id())
                .title("album title 1")
                .build());
        Long albumId = albumDto.id();
        Long artistId = artistDto.id();
        //then
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistDto.id())).isEmpty();
        songifyCrudFacade.addArtistToAlbum(artistId,albumId);
        Set<AlbumDto> albumsByArtistId = songifyCrudFacade.findAlbumsByArtistId(artistDto.id());
        assertThat(albumsByArtistId).isNotEmpty();
        assertThat(albumsByArtistId).extracting(AlbumDto::title).containsExactly(albumDto.title());
    }

    @Test
    void should_return_album_by_id() {
        //given
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(songRequestDto);

        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto.builder()
                .songId(songDto.id())
                .title("album title 1")
                .build());
        Long albumDtoId = albumDto.id();
        //when
        AlbumDto album = songifyCrudFacade.findAlbumDtoById(albumDtoId);
        //then
        assertThat(album).extracting("id","title")
                .containsExactly(albumDtoId,albumDto.title());
    }

    @Test
    void should_throw_exception_when_album_not_found_by_id() {
        ///given
        Long albumId = 2L;
       //when
        //then
        assertThatThrownBy(() -> songifyCrudFacade.findAlbumDtoById(albumId))
                .isInstanceOf(AlbumNotFoundException.class)
                .hasMessageContaining("Album with [%s] not found".formatted(albumId));
    }
}