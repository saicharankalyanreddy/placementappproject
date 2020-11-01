package com.example.ossd_project;

public class Student {

    String username;
    String email;
    String password;
    String sem;
    String cgpa;


    public Student() {
    }

    public Student(String username, String email, String sem, String cgpa, String password) {
        this.username = username;
        this.email = email;
        this.sem = sem;
        this.cgpa = cgpa;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }
}
