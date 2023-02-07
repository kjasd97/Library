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
        List <Book> books = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM book");

            while (resultSet.next()){
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setName(resultSet.getString("name"));
                book.setAuthor(resultSet.getString("author"));
                book.setYear(resultSet.getInt("year"));

                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public Book indexBook(int id){
        Book book = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM BOOK WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            book = new Book();

            book.setId(resultSet.getInt("id"));
            book.setName(resultSet.getString("name"));
            book.setAuthor(resultSet.getString("author"));
            book.setYear(resultSet.getInt("year"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;
    }

    public void saveBook(Book book){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO BOOK (name, author, year) values (?,?,?)");

           preparedStatement.setString(1, book.getName());
           preparedStatement.setString(2, book.getAuthor());
           preparedStatement.setInt(3,book.getYear());
           preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBook(int id, Book updatedBook){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE BOOK SET name=?, author=?, yaer=? WHERE id=?");

            preparedStatement.setString(1,updatedBook.getName());
            preparedStatement.setString(2, updatedBook.getAuthor());
            preparedStatement.setInt(3,updatedBook.getYear());
            preparedStatement.setInt(4,id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBook(int id){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM BOOK WHERE id = ?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Person> getBookOwner(int id){
        Person person = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id WHERE Book.id = ?");
            preparedStatement.setInt(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
            person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setAge(resultSet.getInt("age"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));}

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(person);
    }

    public void release(int id){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE BOOK SET person_id=null where id= ?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void assign(int id, Person person){
        try {
            PreparedStatement preparedStatement=
                    connection.prepareStatement("UPDATE BOOK SET person_id=? where id=?");
            preparedStatement.setInt(1,person.getId());
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


