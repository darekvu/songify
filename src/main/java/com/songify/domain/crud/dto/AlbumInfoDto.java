package com.songify.domain.crud.dto;


import java.util.Set;

public record AlbumInfoDto(Long albumId, Set<ArtistDto> artists, Set<SongDto> songs) {
}
