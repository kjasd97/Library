package spring.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {

    private int id;
    @Min(value = 0, message = "Age should be greater than zero!")
    private int age;
    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 2, max = 40, message = "Name should be between 2 and 40 characters!")
    private String name;
    @NotEmpty(message = "Email should not be empty!")
    @Email(message = "Email should be valid!")
    private String email;

    public Person(){

    }

    public Person(int age, String name, String email) {
        this.age = age;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
