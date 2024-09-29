package com.example.finalproject.repositories;

import com.example.finalproject.models.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MusicRepository extends JpaRepository<Music,Long> {
    @Query("""
  SELECT m FROM Music m WHERE lower(m.songTitle)=lower(:songtitle) 
""")
    public Music getMusicIgnoreCase(@Param("songtitle") String songtitle);

}
