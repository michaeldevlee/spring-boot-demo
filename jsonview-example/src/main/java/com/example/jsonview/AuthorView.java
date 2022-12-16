package com.example.jsonview;

public class AuthorView {
    public interface IdAndName {}
    public interface IdAndNameWithBooks extends IdAndName {}
    public interface AllDetails extends IdAndNameWithBooks {}
}
