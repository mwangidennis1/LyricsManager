package com.example.finalproject.controller;

import com.example.finalproject.models.Music;
import com.example.finalproject.services.MusicService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class MusicWebController {
    @Autowired
    private final MusicService musicService;
    public MusicWebController(MusicService musicService){
        this.musicService=musicService;
    }

    @PostMapping("/getsongtitletwo")
    public String getOneM(@RequestParam("songTitle") String songTitle, Model model){
        Music music=musicService.getOneMusic(songTitle);
        if(music == null){
            return "error.html";
        }
       model.addAttribute("music",music);
       return "myoozik-return";
    }


    @GetMapping("/getsongtitle")
    public String home(Model model){
        model.addAttribute("Music",new Music());
        return "myoozik.html";
    }

}
