package com.example.LibraryApplication.dto;

import lombok.Data;


@Data
public class BookRequest {
    private int id;
    private String name;
    private String author;
    private String publisher;
    private Boolean isAvailable;
}
