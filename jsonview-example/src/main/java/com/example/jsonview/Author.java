package com.example.jsonview;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

public class Author {
    
    @JsonView(AuthorView.IdAndName.class)
    private Integer id;

    @JsonView(AuthorView.IdAndName.class)
    private String firstName;

    @JsonView(AuthorView.IdAndName.class)
    private String lastName;

    @JsonView(AuthorView.AllDetails.class)
    private Date birthDate;

    @JsonView(AuthorView.AllDetails.class)
    private String nationality;

    @JsonView(AuthorView.AllDetails.class)
    private String imageUrl;

    @JsonView(AuthorView.IdAndNameWithBooks.class)
    private List<Book> books;

    public Author(Integer id, String firstName, String lastName, Date birthDate, String nationality, String imageUrl,
            List<Book> books) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.imageUrl = imageUrl;
        this.books = books;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    
    
}
