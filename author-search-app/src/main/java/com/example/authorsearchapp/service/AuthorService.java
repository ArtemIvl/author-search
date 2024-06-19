package com.example.authorsearchapp.service;

import com.example.authorsearchapp.api.ApiResponse;
import com.example.authorsearchapp.entity.Author;
import com.example.authorsearchapp.repository.AuthorRepository;
import com.example.authorsearchapp.DTO.AuthorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    private final String authorApiUrl = "https://openlibrary.org/search/authors.json?q=";

    public Optional<AuthorDTO> findByName(String name) {
        // Check if author exists in the database
        Optional<Author> existingAuthor = authorRepository.findByName(name);
        if (existingAuthor.isPresent()) {
            Author author = existingAuthor.get();
            return Optional.of(new AuthorDTO(author.getId(), author.getName()));
        }

        // If author does not exist in the database, search via API
        Author newAuthor = searchAuthorInApi(name);
        if (newAuthor != null) {
            // Check again by API ID to avoid duplicates
            Optional<Author> existingAuthorByApiId = authorRepository.findByApiId(newAuthor.getApiId());
            if (existingAuthorByApiId.isPresent()) {
                Author author = existingAuthorByApiId.get();
                return Optional.of(new AuthorDTO(author.getId(), author.getName()));
            }

            // Save the new author to the database
            authorRepository.save(newAuthor);
            return Optional.of(new AuthorDTO(newAuthor.getId(), newAuthor.getName()));
        }

        // Return empty if author not found in database or API
        return Optional.empty();
    }

    private Author searchAuthorInApi(String name) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String url = authorApiUrl + name;
        ResponseEntity<ApiResponse> response = restTemplate.getForEntity(url, ApiResponse.class);

        if (response.getBody() != null && response.getBody().getNumFound() > 0) {
            ApiResponse.Doc doc = response.getBody().getDocs().get(0);
            Author author = new Author();
            author.setName(doc.getName());
            author.setApiId(doc.getKey());
            return author;
        }
        return null;
    }
}