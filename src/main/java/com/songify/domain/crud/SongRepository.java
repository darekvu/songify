package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;


 interface SongRepository extends Repository<Song, Long> {

    @Query("SELECT s FROM Song s")
    List<Song> findAll(Pageable pageable);

    @Query("SELECT s FROM Song s WHERE s.id =:songId")
    Optional<Song> findById(Long songId);

    @Modifying
    @Query("DELETE FROM Song s WHERE s.id = :songId")
    void deleteById(Long songId);

    @Modifying
    @Query("UPDATE Song s SET s.name = :#{#newSong.name} WHERE s.id = :songId")
    void updateById(Long songId, Song newSong);

    boolean existsById(Long id);

    Song save(Song song);


    Optional<Song> findByArtistEqualsIgnoreCase(String artistName);
}
