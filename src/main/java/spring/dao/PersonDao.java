package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import spring.models.Book;
import spring.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> showAll() {
       return jdbcTemplate.query("SELECT * FROM PERSON", new PersonMapper());
    }

    public Person index(int id){
       return jdbcTemplate.query("SELECT * FROM PERSON WHERE id=?", new Object[]{id}, new PersonMapper())
                .stream().findAny().orElse(null);
    }


    public void save(Person person) {
      jdbcTemplate.update("INSERT INTO PERSON (age, name, email) VALUES(?,?,?)", person.getAge(),
                person.getName(), person.getEmail());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE PERSON SET age=?, name=?, email=? WHERE id=?", person.getAge(),
                person.getName(), person.getEmail(), id);
    }

    public void delete(int id){
       jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public List<Book> getBooksByPersonId(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id = ?", new Object[]{id},
              new BookMapper());
    }

}