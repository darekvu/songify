package com.songify.song;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SongViewController {

    private Map<Integer, String> database = new HashMap<>();

    @GetMapping("/")
    public String home() {
        return "home.html";
    }
    @GetMapping("/view/songs")
    public String songs(Model model) {
        database.put(1, "In the Name of Love");
        database.put(2, "MockingBird");
        database.put(3, "Despacito");
        database.put(4, "WakaWaka");
        model.addAttribute("songMap", database);
        return "songs.html";
    }
}
