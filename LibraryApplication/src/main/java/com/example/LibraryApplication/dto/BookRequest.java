package com.example.LibraryApplication.dto;

import lombok.Data;


@Data
public class BookRequest {
    private long id;
    private String name;
    private String author;
    private String publisher;
    private Boolean isAvailable;
}
