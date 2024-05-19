package com.songify.song.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Entity
@Setter
@Table(name = "song")
@AllArgsConstructor
@NoArgsConstructor
public class Song extends BaseEntity{
    @Id
    @GeneratedValue(
            generator = "song_id_seq",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "song_id_seq",
            sequenceName = "song_id_seq",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String artist;
    Instant releaseDate;
    @Enumerated(EnumType.STRING)
    private SongLanguage language;
    private Long duration;
    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

}
