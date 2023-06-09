package com.example.jsf_lib.beans;

import com.example.jsf_lib.dao.DAO;
import com.example.jsf_lib.models.Author;
import com.example.jsf_lib.models.Book;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.util.List;

@ManagedBean
@ViewScoped
public class BookBean {

    private Book book = new Book();
    private Integer authorId;
    private Integer bookId;


    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<Book> getBooks() { return new DAO<Book>(Book.class).getAll(); }

    public List<Author> getAuthors() {
        return new DAO<Author>(Author.class).getAll();
    }

    public List<Author> getBookAuthors() {
        return this.book.getAuthors();
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void loadBookById() {
        this.book = new DAO<Book>(Book.class).getByID(this.bookId);
    }

    public void registerAuthor() {
        Author author = new DAO<Author>(Author.class).getByID(this.authorId);
        this.book.appendAuthor(author);
    }

    public String formAuthor() {
        System.out.println("calling author's form");
        return "author?faces-redirect=true";
    }

    public void register() {
        System.out.println("registering book... " + this.book.getTitle());

        if(book.getAuthors().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("author", new FacesMessage("[ERROR] The book needs one or more authors"));
        }

        if (this.book.getId() == null) {
            new DAO<Book>(Book.class).insert(this.book);
        } else {
            new DAO<Book>(Book.class).update(this.book);
        }

        this.book = new Book();
    }

    public void remove(Book book) {
        System.out.println("Removing book...");
        new DAO<Book>(Book.class).remove(book);
    }

    public void removeAuthor(Author author) {
        this.book.removeAuthor(author);
    }

    public void isbnStartWithOne(FacesContext fc, UIComponent component, Object valueParam) throws ValidatorException {
        String value = valueParam.toString();
        if(!value.startsWith("1")){
            throw new ValidatorException(new FacesMessage("[ERROR] ISBN needs to start with one"));
        }
    }
}