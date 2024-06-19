package com.example.authorsearchapp.repository;

import com.example.authorsearchapp.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findByAuthorId(Long authorId);
}
