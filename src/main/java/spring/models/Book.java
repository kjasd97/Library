package spring.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {

    private int id;
    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 2, max = 40, message = "Name should be between 2 and 40 characters!")
    private String name;
    @NotEmpty(message = "Author of book should not be empty!")
    @Size(min = 2, max = 40, message = "Author name should be between 2 and 40 characters!")
    private String author;
    @Min(value = 1500, message = "Year of publication should be greater than 1500!")
    private int year;

    public Book(){

    }

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
