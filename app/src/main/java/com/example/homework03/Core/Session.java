package com.example.homework03.Core;

import com.example.homework03.Models.Major;
import com.example.homework03.Models.Student;

import java.util.ArrayList;
import java.util.HashMap;

public class Session
{
    // Private static variables
    private static Student student;
    private static Major major;
    private static ArrayList<Student> students;
    private static HashMap<String, String> errors;
    private static HashMap<String, String> filters;
    private static String command;
    private static String orderBy;
    private static String orderType;

    // Getters and setters
    public static String getCommand() { return command; }
    public static HashMap<String, String> getErrors() { return errors;}
    public static HashMap<String, String> getFilters() { return filters;}
    public static Major getMajor() { return major; }
    public static Student getStudent() { return student; }
    public static ArrayList<Student> getStudents() { return students;}
    public static String getOrderBy() { return orderBy; }
    public static String getOrderType() { return orderType; }

    public static void setCommand(String s) { command = s; }
    public static void setErrors(HashMap<String, String> e) { errors = e;}
    public static void setFilters(HashMap<String, String> f) { filters = f;}
    public static void setMajor(Major m) { major = m; }
    public static void setStudent(Student s) { student = s; }
    public static void setStudents(ArrayList<Student> s) { students = s; }
    public static void setOrderBy(String s) { orderBy = s; }
    public static void setOrderType(String s) { orderType = s; }
}
