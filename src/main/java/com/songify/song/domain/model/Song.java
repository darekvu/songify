package com.songify.song.domain.model;

import jakarta.persistence.Entity;
import lombok.Builder;

@Builder
@Entity
public class Song() {
    String name;
    String artist;

}
