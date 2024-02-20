package com.github.restclienttask;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RickAndMortyServiceTest {

    @Autowired
    private MockMvc mvc;

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void setUpMockWebServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void shutDownMockWebServer() throws IOException {
        mockWebServer.shutdown();
    }

    @DynamicPropertySource
    static void backendProps(DynamicPropertyRegistry registry) {
        registry.add("rickandmorty.url", () -> mockWebServer.url("/").toString());
    }

    @Test
    @DirtiesContext
    void getAllRickAndMortyChars() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setBody("""
                        {
                             "info": {
                                 "count": 826,
                                 "pages": 42,
                                 "next": "https://rickandmortyapi.com/api/character?page=2",
                                 "prev": null
                             },
                             "results": [
                                 {
                                     "id": 1,
                                     "name": "Rick Sanchez",
                                     "status": "Alive",
                                     "species": "Human",
                                     "type": "",
                                     "gender": "Male",
                                     "origin": {
                                         "name": "Earth (C-137)",
                                         "url": "https://rickandmortyapi.com/api/location/1"
                                     },
                                     "location": {
                                         "name": "Citadel of Ricks",
                                         "url": "https://rickandmortyapi.com/api/location/3"
                                     },
                                     "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                                     "url": "https://rickandmortyapi.com/api/character/1",
                                     "created": "2017-11-04T18:48:46.250Z"
                                 }
                             ]
                         }
                        """)
                .addHeader("Content-Type", "application/json"));
        mvc.perform(MockMvcRequestBuilders.get("/api/rickmorty/characters"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                             {
                                 "id": 1,
                                 "name": "Rick Sanchez",
                                 "status": "Alive",
                                 "species": "Human"
                             }
                         ]
                        """));
    }

    @Test
    @DirtiesContext
    void getRickAndMortyCharsById() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setBody("""
                           {
                               "id": 1,
                               "name": "Rick Sanchez",
                               "status": "Alive",
                               "species": "Human",
                               "type": "",
                               "gender": "Male",
                               "origin": {
                                   "name": "Earth (C-137)",
                                   "url": "https://rickandmortyapi.com/api/location/1"
                               },
                               "location": {
                                   "name": "Citadel of Ricks",
                                   "url": "https://rickandmortyapi.com/api/location/3"
                               },
                               "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                               "url": "https://rickandmortyapi.com/api/character/1",
                               "created": "2017-11-04T18:48:46.250Z"
                           }
                        """)
                .addHeader("Content-Type", "application/json"));
        mvc.perform(MockMvcRequestBuilders.get("/api/rickmorty/characters/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                              
                            {
                              "id": 1,
                              "name": "Rick Sanchez",
                              "status": "Alive",
                              "species": "Human"
                              }
                                                
                        """));
    }

    @Test
    @DirtiesContext
    void getRickAndMortyCharsByStatus() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setBody("""
                        {
                            "info": {
                                "count": 826,
                                "pages": 42,
                                "next": "https://rickandmortyapi.com/api/character?page=2",
                                "prev": null
                            },
                            "results": [
                                {
                                    "id": 1,
                                    "name": "Rick Sanchez",
                                    "status": "Alive",
                                    "species": "Human",
                                    "type": "",
                                    "gender": "Male",
                                    "origin": {
                                        "name": "Earth (C-137)",
                                        "url": "https://rickandmortyapi.com/api/location/1"
                                    },
                                    "location": {
                                        "name": "Citadel of Ricks",
                                        "url": "https://rickandmortyapi.com/api/location/3"
                                    },
                                    "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                                    "url": "https://rickandmortyapi.com/api/character/1",
                                    "created": "2017-11-04T18:48:46.250Z"
                                }
                            ]
                        }
                        """)
                .addHeader("Content-Type", "application/json"));
        mvc.perform(MockMvcRequestBuilders.get("/api/rickmorty/characters/filter?status=Alive"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                             
                        {
                         "results": [
                             {
                                 "id": 1,
                                 "name": "Rick Sanchez",
                                 "status": "Alive",
                                 "species": "Human"
                             }
                             ]
                             }
                        """));
    }
}