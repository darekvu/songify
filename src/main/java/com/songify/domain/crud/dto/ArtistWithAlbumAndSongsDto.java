package com.songify.domain.crud.dto;

import java.util.Set;

public record ArtistWithAlbumAndSongsDto(Long artistId, String artistName,Set<AlbumDto> albums, Set<SongDto> songs) {
}
