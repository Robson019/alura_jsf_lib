package com.example.jsf_lib.dao;

import com.example.jsf_lib.models.Author;
import com.example.jsf_lib.models.Book;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;

public class Fixtures {
    public static void main(String[] args) {
        EntityManager em = new JPAUtil().getEntityManager();

        em.getTransaction().begin();

        Author assis = generateAuthor("Machado de Assis");
        em.persist(assis);

        Author amado = generateAuthor("Jorge Amado");
        em.persist(amado);

        Author coelho = generateAuthor("Paulo Coelho");
        em.persist(coelho);

        Author lobato = generateAuthor("Monteiro Lobato");
        em.persist(lobato);

        Book casmurro = generateBook("978-8-52-504464-8", "Dom Casmurro",
                "10/01/1899", 24.90, assis);
        Book memorias = generateBook("978-8-50-815415-9",
                "Memorias Postumas de Bras Cubas", "01/01/1881", 19.90, assis);
        Book quincas = generateBook("978-8-50-804084-1", "Quincas Borba",
                "01/01/1891", 16.90, assis);

        em.persist(casmurro);
        em.persist(memorias);
        em.persist(quincas);

        Book alquemista = generateBook("978-8-57-542758-3", "O Alquimista",
                "01/01/1988", 19.90, coelho);
        Book brida = generateBook("978-8-50-567258-7", "Brida", "01/01/1990",
                12.90, coelho);
        Book valkirias = generateBook("978-8-52-812458-8", "As Valkirias",
                "01/01/1992", 29.90, coelho);
        Book maao = generateBook("978-8-51-892238-9", "O Diario de um Mago",
                "01/01/1987", 9.90, coelho);

        em.persist(alquemista);
        em.persist(brida);
        em.persist(valkirias);
        em.persist(maao);

        Book capitaes = generateBook("978-8-50-831169-1", "Capitaes da Areia",
                "01/01/1937", 6.90, amado);
        Book flor = generateBook("978-8-53-592569-9",
                "Dona Flor e Seus Dois Maridos", "01/01/1966", 18.90, amado);

        em.persist(capitaes);
        em.persist(flor);

        em.getTransaction().commit();
        em.close();

    }

    private static Author generateAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        return author;
    }

    private static Book generateBook(String isbn, String title, String releaseAt,
                                   double price, Author author) {
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setReleaseAt(parseData(releaseAt));
        book.setPrice(price);
        book.appendAuthor(author);
        return book;
    }

    private static Calendar parseData(String data) {
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
