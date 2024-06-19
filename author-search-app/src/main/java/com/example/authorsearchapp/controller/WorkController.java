package com.example.authorsearchapp.controller;

import com.example.authorsearchapp.DTO.WorkDTO;
import com.example.authorsearchapp.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/works")
public class WorkController {
    @Autowired
    private WorkService workService;

    @GetMapping("/search")
    public List<WorkDTO> searchWorksByAuthorId(@RequestParam Long authorId) {
        return workService.findByAuthorId(authorId);
    }
}
