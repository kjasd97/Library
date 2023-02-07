package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import spring.models.Book;
import spring.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Book> showAll(){
        return jdbcTemplate.query("SELECT * FROM BOOK", new BookMapper());
    }

    public Book indexBook(int id){
        return jdbcTemplate.query("SELECT * FROM BOOK WHERE id = ?", new Object[]{id}, new BookMapper())
                .stream().findAny().orElse(null);
    }

    public void saveBook(Book book){
       jdbcTemplate.update("INSERT INTO BOOK (name, author, year) VALUES (?,?,?)", book.getName(),book.getAuthor(),
               book.getYear());
    }

    public void updateBook(int id, Book updatedBook){
        jdbcTemplate.update("UPDATE BOOK SET name=?, author=?, yer=? WHERE id=?",
                updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    public void deleteBook(int id){
        jdbcTemplate.update("DELETE FROM BOOK WHERE id = ?",id);
    }

    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id " +
                        "WHERE Book.id = ?", new Object[]{id}, new PersonMapper()).stream().findAny();
    }

    public void release(int id){
    jdbcTemplate.update("UPDATE BOOK SET person_id=NULL WHERE id=?", id);
    }

    public void assign(int id, Person person){
        jdbcTemplate.update("UPDATE BOOK SET person_id=? WHERE id =?", person.getId(), id);
    }

}


