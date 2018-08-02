package database.entity;

import java.util.Date;

public class Cooperator {

    private int id;
    private String name;
    private String email;
    private int salary;
    private Date date;

    public Cooperator(int id, String name, String email, int salary, Date date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
