package com.example.authorsearchapp.service;

import com.example.authorsearchapp.entity.Work;
import com.example.authorsearchapp.entity.Author;
import com.example.authorsearchapp.repository.WorkRepository;
import com.example.authorsearchapp.repository.AuthorRepository;
import com.example.authorsearchapp.DTO.WorkDTO;
import com.example.authorsearchapp.api.ApiWorksResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkService {
    private static final Logger logger = LoggerFactory.getLogger(WorkService.class);

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private final String worksApiUrl = "https://openlibrary.org/authors/";

    public List<WorkDTO> findByAuthorId(Long authorId) {
        Optional<Author> authorOptional = authorRepository.findById(authorId);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            List<Work> works = workRepository.findByAuthorId(authorId);
            if (works.isEmpty()) {
                works = fetchWorksFromApi(author);
                saveWorksToDatabase(works);
            }
            return works.stream()
                    .map(work -> new WorkDTO(work.getId(), work.getTitle()))
                    .collect(Collectors.toList());
        } else {
            logger.error("Author not found for ID: " + authorId);
            return null; // Return null if author not found
        }
    }

    private List<Work> fetchWorksFromApi(Author author) {
        String apiId = author.getApiId();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String url = worksApiUrl + apiId + "/works.json";
        ResponseEntity<ApiWorksResponse> response = restTemplate.getForEntity(url, ApiWorksResponse.class);

        if (response.getBody() != null && response.getBody().getEntries() != null) {
            return response.getBody().getEntries().stream()
                    .map(dto -> {
                        Work work = new Work();
                        work.setTitle(dto.getTitle());
                        work.setAuthor(author); // Set the author for each work
                        return work;
                    })
                    .collect(Collectors.toList());
        }
        return List.of(); // Return an empty list if no works are found in the API
    }

    private void saveWorksToDatabase(List<Work> works) {
        if (!works.isEmpty()) {
            workRepository.saveAll(works); // Save all fetched works to the database
        }
    }
}
