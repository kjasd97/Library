package spring.dao;

import org.springframework.stereotype.Component;
import spring.models.Book;
import spring.models.Person;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {

    private static final String URL = "jdbc:postgresql://localhost:5432/library";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


}

    public List<Person> showAll() {
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PERSON");


            while (resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setAge(resultSet.getInt("age"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }

    public Person index(int id){
        Person person = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * from Person where id =?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setAge(resultSet.getInt("age"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }


    public void save(Person person) {
        try {
            PreparedStatement preparedStatement
                    = connection.prepareStatement("INSERT INTO PERSON (age, name, email) VALUES (?,?,?)");


            preparedStatement.setInt(1, person.getAge());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getEmail());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, Person person) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE PERSON SET age=?, name=?, email=? WHERE id=?");

            preparedStatement.setInt(1, person.getAge());
            preparedStatement.setString(2,person.getName());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setInt(4,id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id){
        try {
           PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM PERSON WHERE id =?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> getBooksByPersonId(int id){
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement preparedStatement
                    = connection.prepareStatement("SELECT * FROM BOOK WHERE person_id=?");
            preparedStatement.setInt(1,id);

           Book book= new Book();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
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

}