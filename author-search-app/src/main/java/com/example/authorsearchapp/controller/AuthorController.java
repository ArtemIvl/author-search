package com.example.authorsearchapp.controller;

import com.example.authorsearchapp.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.authorsearchapp.DTO.AuthorDTO;

import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/search")
    public Optional<AuthorDTO> searchAuthorByName(@RequestParam String name) {
        return authorService.findByName(name);
    }
}
