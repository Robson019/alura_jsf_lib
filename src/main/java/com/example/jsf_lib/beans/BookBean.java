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
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

@ManagedBean
@ViewScoped
public class BookBean implements Serializable {

    private Book book = new Book();
    private Integer authorId;
    private Integer bookId;
    private List<Book> books;

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

    public List<Book> getBooks() {
        DAO<Book> dao = new DAO<Book>(Book.class);
        if(this.books == null) {
            this.books = dao.getAll();
        }
        return books;
    }

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
            return;
        }

        DAO<Book> dao = new DAO<Book>(Book.class);
        if (this.book.getId() == null) {
            dao.insert(this.book);
            this.books = dao.getAll();
        } else {
            dao.update(this.book);
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

    public boolean lowestPrice(Object columnValue, Object filter, Locale locale) {

        //tirando espaços do filtro
        String textTyped = (filter == null) ? null : filter.toString().trim();

        System.out.println("Filtering by " + textTyped + ", value of element: " + columnValue);

        // o filtro é nulo ou vazio?
        if (textTyped == null || textTyped.equals("")) {
            return true;
        }

        // elemento da tabela é nulo?
        if (columnValue == null) {
            return false;
        }

        try {
            // fazendo o parsing do filtro para converter para Double
            Double priceTyped = Double.valueOf(textTyped);
            Double columnPrice = (Double) columnValue;

            // comparando os valores, compareTo devolve um valor negativo se o value é menor do que o filtro
            return columnPrice.compareTo(priceTyped) < 0;

        } catch (NumberFormatException e) {

            // usuario nao digitou um numero
            return false;
        }
    }
}