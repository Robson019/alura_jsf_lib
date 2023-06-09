package com.example.jsf_lib.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;
    private String isbn;
    private double price;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar releaseAt = Calendar.getInstance();

    @ManyToMany(fetch=FetchType.EAGER)
    private List<Author> authors = new ArrayList<Author>();

    public List<Author> getAuthors() {
        return authors;
    }

    public void appendAuthor(Author author) {
        this.authors.add(author);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
    }

    public Book() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Calendar getReleaseAt() {
        return releaseAt;
    }

    public void setReleaseAt(Calendar releaseAt) {
        this.releaseAt = releaseAt;
    }
}