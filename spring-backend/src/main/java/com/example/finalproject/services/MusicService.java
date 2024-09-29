package com.example.finalproject.services;

import com.example.finalproject.models.Music;
import com.example.finalproject.repositories.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicService {
    @Autowired
    private final MusicRepository musicRepository;
    public MusicService(MusicRepository musicRepository){
        this.musicRepository=musicRepository;
    }
    public List<Music> getMusic(){
        return  musicRepository.findAll();
    }
    public void setMusic(Music music){
        musicRepository.save(music);
    }
    public Music getOneMusic(String songtitle){
        return  musicRepository.getMusicIgnoreCase(songtitle);
    }
    public Music editMusic(Music newMusic,long id){
        return musicRepository.findById(id)
                .map(music -> {
                    music.setSongTitle(newMusic.getSongTitle());
                    music.setLyrics(newMusic.getLyrics());
                    return musicRepository.save(music);
                }).orElseGet(
                        ()->{
                            newMusic.setId(id);
                            return musicRepository.save(newMusic);
                        }
                );
    }
    public void delete(long id){
        musicRepository.deleteById(id);
    }
    public boolean getSongtitle(String songtitle){
        Music music=musicRepository.getMusicIgnoreCase(songtitle);
        if(music==null){
            return false;
        }
        String m=music.getSongTitle();
        if(m.equals(songtitle)){
            return true;
        }
        return false;
    }
}
