package com.example.jsonview;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    AuthorRepository repo;
    
    @JsonView(AuthorView.AllDetails.class)
    @GetMapping("/all")
    public ResponseEntity<List<Author>> getAllDetails() {
        return new ResponseEntity<>(repo.getUsers(), HttpStatus.OK);
    }

    @JsonView(AuthorView.IdAndName.class)
    @GetMapping()
    public ResponseEntity<List<Author>> getIdAndNamesOnly() {
        return new ResponseEntity<>(repo.getUsers(), HttpStatus.OK);
    } 

    @JsonView(AuthorView.IdAndNameWithBooks.class)
    @GetMapping("/withBooks")
    public ResponseEntity<List<Author>> getIdAndNamesWithBooks() {
        return new ResponseEntity<>(repo.getUsers(), HttpStatus.OK);
    }    
}
