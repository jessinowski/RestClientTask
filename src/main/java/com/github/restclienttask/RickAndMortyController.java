package com.github.restclienttask;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rickmorty")
@RequiredArgsConstructor
public class RickAndMortyController {

    private final RickAndMortyService rickAndMortyService;

    @GetMapping("/characters")
    public List<Character> getAllRickAndMortyChars() {
        return rickAndMortyService.getAllRickAndMortyChars().results();
    }

    @GetMapping("/characters/{id}")
    public Character getRickAndMortyCharsById(@PathVariable int id) {
        return rickAndMortyService.getRickAndMortyCharsById(id);
    }

    @GetMapping("/characters/filter")
    public ApiResponse getRickAndMortyCharsByStatus(@RequestParam String status){
        return rickAndMortyService.getRickAndMortyCharsByStatus(status);
    }

//    @GetMapping("/species-statistic")
//    public int getNumberOfCharactersBySpecies(@RequestParam String species){
//        return rickAndMortyService.getNumberOfCharactersBySpecies(species);
//    }
}
