package com.example.homework03.Models;

public class Student
{
    int id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private float gpa;
    private Major major;
    //---------------------------------------------------------------------------------------------
    public Student()
    {
        this.id = -1;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.age = -1;
        this.gpa = -1;
        this.major = new Major();
    }
    //---------------------------------------------------------------------------------------------
    public Student(int id, String firstName, String lastName, String email, int age, float gpa,
                   Major major)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.gpa = gpa;
        this.major = major;
    }
    //---------------------------------------------------------------------------------------------
    public int getId() { return this.id; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getEmail() { return this.email; }
    public int getAge() { return this.age; }
    public float getGpa() { return this.gpa; }
    public Major getMajor() { return this.major; }
    //---------------------------------------------------------------------------------------------
    public void setId(int id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setAge(int age) { this.age = age; }
    public void setGpa(float gpa) { this.gpa = gpa; }
    public void setMajor(Major major) { this.major = major; }
    //---------------------------------------------------------------------------------------------
}
