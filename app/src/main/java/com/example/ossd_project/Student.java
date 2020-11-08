package com.example.ossd_project;

public class Student {

    String FullName;
    String Email;
    String RegNo;
    String Resume;



    public Student() {
    }

    public Student(String fullName, String email, String regNo, String resume) {
        FullName = fullName;
        Email = email;
        RegNo = regNo;
        Resume = resume;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String regNo) {
        RegNo = regNo;
    }

    public String getResume() {
        return Resume;
    }

    public void setResume(String resume) {
        Resume = resume;
    }
}
