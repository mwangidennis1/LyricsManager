package com.example.finalproject.controller;

import com.example.finalproject.models.Music;
import com.example.finalproject.services.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MusicController {
    @Autowired
    private final MusicService musicService;
    public MusicController(MusicService musicService){
        this.musicService=musicService;
    }
    @PostMapping("/create")
    public ResponseEntity<String> createMusic(@RequestBody Music music){
        String songtitle=music.getSongTitle();
        if(musicService.getSongtitle(songtitle)){
            return ResponseEntity.ok("The record exists");
        }
        musicService.setMusic(music);
        return ResponseEntity.ok("Created  a song");
    }
    @GetMapping("/all")
    public ResponseEntity<List<Music>> getthem(){
        List<Music> x =musicService.getMusic();
        return ResponseEntity.ok(x);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editItem(@RequestBody Music music,@PathVariable Long id){
        musicService.editMusic(music,id);
        return ResponseEntity.ok("Successfully updated the Music");
    }
    @DeleteMapping("/deletemusic/{id}")
    public void deleteusic(@PathVariable Long id){
        musicService.delete(id);
    }

}
