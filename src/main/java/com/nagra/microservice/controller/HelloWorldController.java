package com.nagra.microservice.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
@Controller
public class HelloWorldController {

    private final WebClient webClient = WebClient.create("https://rickandmortyapi.com/api/");

    @GetMapping(value = "/api/v1/characters/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CharacterResponse> getCharacters(@RequestParam(required = false) String name) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("character")
                        .queryParamIfPresent("name", java.util.Optional.ofNullable(name))
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CharacterListResponse.class)
                .block()
                .getResults();
    }
}
