package com.nagra.microservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@Controller
public class HelloWorldController {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);
    private final WebClient webClient = WebClient.create("https://rickandmortyapi.com/api/");

    @GetMapping(value = "/api/v1/characters/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CharacterResponse> getCharacters(@RequestParam(required = false) String name) {
        logger.info("Request received for fetching characters");
        logger.info("Name parameter: {}", name);

        List<CharacterResponse> characters = Collections.emptyList();

        try {
            characters = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("character")
                            .queryParamIfPresent("name", java.util.Optional.ofNullable(name))
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(CharacterListResponse.class)
                    .block()
                    .getResults();

            if (characters != null && !characters.isEmpty()) {
                logger.info("Fetched {} characters", characters.size());
                for (CharacterResponse character : characters) {
                    logger.info("Character fetched: {}", character.getName());
                }
            } else {
                logger.info("No characters found");
            }
        } catch (Exception e) {
            logger.error("NO SUCH CHARACTER/Error occurred while fetching characters: {}", e.getMessage());
        }

        return characters;
    }
}
