package com.example.jsf_lib.beans;

import com.example.jsf_lib.dao.DAO;
import com.example.jsf_lib.models.Author;
import com.example.jsf_lib.models.Book;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean
@ViewScoped
public class AuthorBean {
    private Author author = new Author();
    private Integer authorId;

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public void loadAuthorById() {
        this.author = new DAO<Author>(Author.class).getByID(this.authorId);
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Author> getAuthors() { return new DAO<Author>(Author.class).getAll(); }

    public String register() {
        System.out.println("Registering autor "+ this.author.getName());

        if (this.author.getId() == null) {
            new DAO<Author>(Author.class).insert(this.author);
        } else {
            new DAO<Author>(Author.class).update(this.author);
        }


        this.author = new Author();

        return "book?faces-redirect=true";
    }

    public void remove(Author author) {
        System.out.println("Removing author...");
        new DAO<Author>(Author.class).remove(author);
    }

    public String formBook() {
        System.out.println("calling book's form");
        return "book?faces-redirect=true";
    }
}