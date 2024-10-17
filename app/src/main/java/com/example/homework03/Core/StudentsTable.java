package com.example.homework03.Core;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.homework03.Models.Major;
import com.example.homework03.Models.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StudentsTable
{
    private final Database database;
    private final String tableName;
    private final MajorsTable majors;
    private String orderColumn;
    private String orderType;
    private ArrayList<String> columns;
    private ArrayList<String> errors;

    public StudentsTable(Context c)
    {
        // Get the database instance
        this.database = new Database(c);

        // Set the table name
        this.tableName = "students";

        // Set the columns
        this.columns = new ArrayList<String>();
        this.columns.add("id");
        this.columns.add("first_name");
        this.columns.add("last_name");
        this.columns.add("email");
        this.columns.add("age");
        this.columns.add("gpa");
        this.columns.add("major_id");

        // By default, order by id
        this.orderColumn = this.columns.get(0);

        // By default, order ascending
        this.orderType = "asc";

        // Set the errors
        this.errors = new ArrayList<String>();

        // Because this has a majors object, we need a new majors table
        this.majors = new MajorsTable(c);

    }
    //---------------------------------------------------------------------------------------------
    public void setOrderColumn(String s) { this.orderColumn = s; }
    public void setOrderType(String s) { this.orderType = s;}
    //---------------------------------------------------------------------------------------------
    public void delete(Student s)
    {
        // Get the database
        SQLiteDatabase db = this.database.getWritableDatabase();

        // Create the delete string
        String sql = "delete from students where id = " + s.getId() + ";";

        // Execute delete
        db.execSQL(sql);
    }
    //---------------------------------------------------------------------------------------------
    public ArrayList<Student> findWhere(HashMap<String, String> filter)
    {
        // If there is no where statement, return the for all statement
        if(filter.isEmpty()) { return this.findAll(); }

        // Start with a fresh, empty array
        ArrayList<Student> students = new ArrayList<Student>();

        // Create the query
        String sql = "select * from " + this.tableName + " where ";

        // Here we need to do a for each in the hashmap
        for(String key : filter.keySet())
        {
            // Get the value
            String value = filter.get(key);

            // check if the value is empty - if so, move to the next item
            if(value.isEmpty()) { continue; }

            // Depending on the key, add to our query
            if(key.equals("first_name"))
            {
                sql = sql + key + " like '" + value + "' and ";
            }
            else if (key.equals("last_name"))
            {
                sql = sql + key + " like '" + value + "' and ";
            }
            else if (key.equals("major_id"))
            {
                sql = sql + "major_id=" + value + " and ";
            }
            else if (key.equals("greater_than"))
            {
                sql = sql + "gpa >= " + value + " and ";
            }
            else if (key.equals("less_than"))
            {
                sql = sql + "gpa <= " + value + " and ";
            }
            else if (key.equals("id"))
            {
                sql = sql + " id=" + value + " and ";
            }


        }

        // Remove the trailing && from the sql statement
        sql = sql.substring(0, sql.length() -5);

        // Add the Order type and column
        sql = sql + " order by " + this.orderColumn + " " + this.orderType;

        // Test
        Log.d("SQL Command", sql);

        // Grab the database
        SQLiteDatabase db = this.database.getReadableDatabase();

        // Execute the query
        Cursor c = db.rawQuery(sql, null);

        // Guard clause - no students were found return an empty array
        if (c.getCount() < 1) { return students;}

        // Iterate through all objects
        while(c.moveToNext())
        {
            // Create an error major object
            Major m = new Major("Error", "ERR");

            // Set the ID
            m.setId(1);

            // First we create a new error object type
            Student s = new Student(999999, "Error", "Error", "Error@error", -1, 0, m);

            // Set the error Major
            s.setMajor(m);

            // Fill in all values except Major
            s.setId(c.getInt(0));
            s.setFirstName(c.getString(1));
            s.setLastName(c.getString(2));
            s.setEmail(c.getString(3));
            s.setAge(c.getInt(4));
            s.setGpa(c.getFloat(5));

            // Find the major and set it
            m = this.majors.findById(c.getInt(6));
            s.setMajor(m);

            // Add our student to the array list
            students.add(s);
        }

        // Close the cursor
        c.close();

        // close the database
        db.close();

        // Return our array
        return students;
    }
    public ArrayList<Student> findAll()
    {
        // Start with a fresh, empty array
        ArrayList<Student> students = new ArrayList<Student>();

        // Create the query
        String sql = "select * from " + this.tableName +
                " order by " + this.orderColumn + " " + this.orderType + ";";

        // Grab the database
        SQLiteDatabase db = this.database.getReadableDatabase();

        // Execute the query
        Cursor c = db.rawQuery(sql, null);

        // Guard clause - no students were found return an empty array
        if (c.getCount() < 1) { return students;}

        // Iterate through all objects
        while(c.moveToNext())
        {
            // Create an error major object
            Major m = new Major("Error", "ERR");

            // Set the ID
            m.setId(1);

            // First we create a new error object type
            Student s = new Student(999999, "Error", "Error", "Error@error", -1, 0, m);

            // Set the error Major
            s.setMajor(m);

            // Fill in all values except Major
            s.setId(c.getInt(0));
            s.setFirstName(c.getString(1));
            s.setLastName(c.getString(2));
            s.setEmail(c.getString(3));
            s.setAge(c.getInt(4));
            s.setGpa(c.getFloat(5));

            // Find the major and set it
            m = this.majors.findById(c.getInt(6));
            s.setMajor(m);

            // Add our student to the array list
            students.add(s);
        }

        // Close the cursor
        c.close();

        // close the database
        db.close();

        // Return our array
        return students;
    }
    //---------------------------------------------------------------------------------------------
    public Student findById(int id)
    {
        // Create an error major object
        Major m = new Major("Error", "ERR");

        // Set the ID
        m.setId(1);

        // First we create a new error object type
        Student s = new Student(999999, "Error", "Error", "Error@error", -1, 0, m);

        // Set the error Major
        s.setMajor(m);

        // Create the query
        String sql = "select * from " + this.tableName +
                " where id ='" + id + "';";

        // Get the database
        SQLiteDatabase db = this.database.getReadableDatabase();

        // Execute the query
        Cursor c = db.rawQuery(sql, null);

        // Guard clause - no student was found so return the error student
        if(c.getCount() < 1) { return s;}

        // Move the cursor to the first object
        c.moveToFirst();

        // Fill in all values except Major
        s.setId(c.getInt(0));
        s.setFirstName(c.getString(1));
        s.setLastName(c.getString(2));
        s.setEmail(c.getString(3));
        s.setAge(c.getInt(4));
        s.setGpa(c.getFloat(5));

        // Find the major and set it
        m = this.majors.findById(c.getInt(6));
        s.setMajor(m);

        // Close the cursor
        c.close();

        // close the database
        db.close();

        // return our value
        return s;
    }
    //---------------------------------------------------------------------------------------------
    public int getNextId()
    {
        // If the table is empty - we return the first valid number
        if(this.numberOfRecords() == 0) { return 100001; }

        // Get the database
        SQLiteDatabase db = this.database.getReadableDatabase();

        // Otherwise, make an SQL query to sort all records in descending order
        String sql = "select * from " + this.tableName + " order by id desc;";

        // Execute the query
        Cursor c = db.rawQuery(sql, null);

        // There should be at least one record - we already checked - so move to the record
        c.moveToNext();

        // Grab the Highest Student ID and increment by one
        int nextId = c.getInt(0) + 1;

        // Close the cursor
        c.close();

        // close the database
        db.close();

        // Return our value
        return nextId;
    }
    //---------------------------------------------------------------------------------------------
    public boolean idExists(int id)
    {
        // Create return boolean
        boolean r = false;

        // Create the query
        String sql = "select * from " + this.tableName +
                " where id ='" + id + "';";

        // Get the database
        SQLiteDatabase db = this.database.getReadableDatabase();

        // Execute the query
        Cursor c = db.rawQuery(sql, null);

        // If we have a result, then we know it exists
        if(c.getCount() > 0) { r = true; }

        // Close the cursor
        c.close();

        // close the database
        db.close();

        // return our value
        return r;
    }
    //---------------------------------------------------------------------------------------------
    public int numberOfRecords()
    {
        // Get an instance of the readable database
        SQLiteDatabase db = this.database.getReadableDatabase();

        // Count how many records are in the table
        int n = (int) DatabaseUtils.queryNumEntries(db, this.tableName);

        // Close the database
        db.close();

        // Return the value
        return n;
    }
    //---------------------------------------------------------------------------------------------
    public boolean validateInsert(@NonNull Student s)
    {
        // Create a new hashmap for errors
        HashMap<String, String> errors = new HashMap<>();

        // First, validate the id
        if(s.getId() < 100000)
        {
            errors.put("id", "Id must be over 100000");
        }
        else if (idExists(s.getId()))
        {
            errors.put("id", "Id already exists!");
        }

        // Next, validate the first name
        if(s.getFirstName().isEmpty())
        {
            errors.put("first name", "First name cannot be empty");
        }

        // Next, validate the prefix
        if(s.getLastName().isEmpty())
        {
            errors.put("last name", "Last name cannot be empty");
        }

        // Next, validate the email
        if(s.getEmail().isEmpty())
        {
            errors.put("email", "Email cannot be empty");
        }
        else if (s.getEmail().indexOf('@') == -1)
        {
           errors.put("email", "No @ symbol present!");
        }

        // Next, validate the Age
        if(s.getAge() < 1)
        {
            errors.put("age", "Age must be older than 1");
        }

        // Next, validate the GPA
        if(s.getGpa() < 0.0)
        {
            errors.put("gpa", "GPA must be between 0.0 and 4.0");
        }
        else if (s.getGpa() > 4.0)
        {
            errors.put("gpa", "GPA must be between 0.0 and 4.0");
        }

        // Set the session errors
        Session.setErrors(errors);

        // return true if there are no errors
        return errors.isEmpty();
    }
    //---------------------------------------------------------------------------------------------
    public boolean validateUpdate(@NonNull Student s)
    {
        // Create a new hashmap for errors
        HashMap<String, String> errors = new HashMap<>();

        // First, validate the id
        if(s.getId() < 100000)
        {
            errors.put("id", "Id must be over 100000");
        }

        // Next, validate the first name
        if(s.getFirstName().isEmpty())
        {
            errors.put("first name", "First name cannot be empty");
        }

        // Next, validate the prefix
        if(s.getLastName().isEmpty())
        {
            errors.put("last name", "Last name cannot be empty");
        }

        // Next, validate the email
        if(s.getEmail().isEmpty())
        {
            errors.put("email", "Email cannot be empty");
        }
        else if (s.getEmail().indexOf('@') == -1)
        {
            errors.put("email", "No @ symbol present!");
        }

        // Next, validate the Age
        if(s.getAge() < 1)
        {
            errors.put("age", "Age must be older than 1");
        }

        // Next, validate the GPA
        if(s.getGpa() < 0.0)
        {
            errors.put("gpa", "GPA must 0.0 or greater");
        }
        else if (s.getGpa() > 4.0)
        {
            errors.put("gpa", "GPA must be less than 4.0");
        }

        // Set the session errors
        Session.setErrors(errors);

        // return true if there are no errors
        return errors.isEmpty();
    }
    //---------------------------------------------------------------------------------------------
    public void insert(@NonNull Student s)
    {
        // Get the database
        SQLiteDatabase db = this.database.getWritableDatabase();

        // Get the major
        Major m = s.getMajor();

        // Create the inset string
        String sql = "insert into students " +
                "(id, first_name, last_name, email, age, gpa, major_id) VALUES ('" +
                s.getId() + "','" +
                s.getFirstName() + "','" +
                s.getLastName() + "','" +
                s.getEmail() + "','" +
                s.getAge() + "','" +
                s.getGpa() + "','" +
                m.getId() +
                "');";

        // Execute insert
        db.execSQL(sql);
    }
    //---------------------------------------------------------------------------------------------
    public void update(@NonNull Student s)
    {
        // Get the database
        SQLiteDatabase db = this.database.getWritableDatabase();

        // Get the major
        Major m = s.getMajor();

        // Create the inset string
        String sql = "update students set " +
                "first_name = '"    + s.getFirstName()  + "'," +
                "last_name = '"     + s.getLastName()   + "'," +
                "email = '"         + s.getEmail()      + "'," +
                "age = '"           + s.getAge()        + "'," +
                "gpa = '"           + s.getGpa()        + "'," +
                "major_id = '"      + m.getId()         + "'" +
                "where id = "       + s.getId() +
                ";";

        // Execute insert
        db.execSQL(sql);
    }
    //---------------------------------------------------------------------------------------------
}
