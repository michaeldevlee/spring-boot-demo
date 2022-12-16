package com.example.jsonview;

import com.fasterxml.jackson.annotation.JsonView;

public class Book {
    
    @JsonView(AuthorView.IdAndNameWithBooks.class)
    private String isbn;

    @JsonView(AuthorView.IdAndNameWithBooks.class)
    private String title;

    @JsonView(AuthorView.AllDetails.class)
    private String publicationDate;

    @JsonView(AuthorView.AllDetails.class)
    private String description;

    public Book(String isbn, String title, String publicationDate, String description) {
        this.isbn = isbn;
        this.title = title;
        this.publicationDate = publicationDate;
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}
