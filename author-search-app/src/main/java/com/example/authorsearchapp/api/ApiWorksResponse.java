package com.example.authorsearchapp.api;

import com.example.authorsearchapp.DTO.WorkDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiWorksResponse {
    @JsonProperty("entries")
    private List<WorkDTO> entries;

    public List<WorkDTO> getEntries() {
        return entries;
    }

    public void setEntries(List<WorkDTO> entries) {
        this.entries = entries;
    }
}
