package com.example.authorsearchapp.entity;

import jakarta.persistence.*;

@Entity
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private Author author;

   public Long getId() {
       return id;
   }

   public void setId(Long id) {
       this.id = id;
   }

   public String getTitle() {
       return title;
   }

   public void setTitle(String title) {
       this.title = title;
   }

   public Author getAuthor() {
       return author;
   }

   public void setAuthor(Author author) {
       this.author = author;
   }
}
