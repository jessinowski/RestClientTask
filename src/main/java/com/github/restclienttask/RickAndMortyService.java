package com.github.restclienttask;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RickAndMortyService {

    private final RestClient restClient;

    public RickAndMortyService(@Value("${rickandmorty.url}") String basicUrl){
        this.restClient = RestClient.create(basicUrl);
    }


    public ApiResponse getAllRickAndMortyChars() {
        ApiResponse response = restClient.get()
                .uri("/character")
                .retrieve()
                .body(ApiResponse.class);
        return response;
    }

    public Character getRickAndMortyCharsById(int id) {
        Character response = restClient.get()
                .uri("/character/" + id)
                .retrieve()
                .body(Character.class);
        return response;
    }

    public ApiResponse getRickAndMortyCharsByStatus(String status) {
        ApiResponse response = restClient.get()
                .uri("/character/?status="+status)
                .retrieve()
                .body(ApiResponse.class);
        return response;
    }

//    public int getNumberOfCharactersBySpecies(String species) {
//       ApiResponse response = restClient.get()
//                .uri("/character/?species="+species)
//                .retrieve()
//                .body(ApiResponse.class);
//        return response.results().size();
//    }
}
