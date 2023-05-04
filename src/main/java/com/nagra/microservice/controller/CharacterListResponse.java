package com.nagra.microservice.controller;
import java.util.List;
public class CharacterListResponse {
    private List<CharacterResponse> results;

    public List<CharacterResponse> getResults() {
        return results;
    }

    public void setResults(List<CharacterResponse> results) {
        this.results = results;
    }
}


