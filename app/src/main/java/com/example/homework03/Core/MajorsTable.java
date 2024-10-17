package com.example.homework03.Core;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.example.homework03.Models.Major;

import java.util.ArrayList;
import java.util.HashMap;

public class MajorsTable
{
    private final Database database;
    private final String tableName;
    private String orderColumn;
    private String orderType;
    private final ArrayList<String> columns;
    private ArrayList<String> errors;

    public MajorsTable(Context c)
    {
        // Get the database instance
        this.database = new Database(c);

        // Set the table name
        this.tableName = "majors";

        // Set the columns
        this.columns = new ArrayList<String>();
        this.columns.add("id");
        this.columns.add("name");
        this.columns.add("prefix");

        // By default, order by id
        this.orderColumn = this.columns.get(0);

        // By default, order ascending
        this.orderType = "asc";

        // Set the errors
        this.errors = new ArrayList<String>();

    }
    //---------------------------------------------------------------------------------------------
    public void setOrderColumn(String s) { this.orderColumn = s; }
    public void setOrderType(String s) { this.orderType = s;}
    //---------------------------------------------------------------------------------------------
    public ArrayList<Major> findAll()
    {
        // Start with a fresh, empty array
        ArrayList<Major> majors = new ArrayList<Major>();

        // Create the query
        String sql = "select * from " + this.tableName +
                " order by " + this.orderColumn + " " + this.orderType;

        // Grab the database
        SQLiteDatabase db = this.database.getReadableDatabase();

        // Execute the query
        Cursor c = db.rawQuery(sql, null);

        // Guard clause - no students were found return an empty array
        if (c.getCount() < 1) { return majors;}

        // Iterate through all objects
        while (c.moveToNext())
        {
            // Create an error major object
            Major m = new Major("Error", "ERR");

            // Fill in all major values
            m.setId(c.getInt(0));
            m.setName(c.getString(1));
            m.setPrefix(c.getString(2));

            // add the major to the list
            majors.add(m);
        }

        // Close the cursor
        c.close();

        // close the database
        db.close();

        // Return our array
        return majors;
    }
    //---------------------------------------------------------------------------------------------
    public Major findById(int id)
    {
        // First we create a new error object type
        Major m = new Major("Error", "ERR");

        // Create the query
        String sql = "select * from " + this.tableName +
                " where id ='" + id + "';";

        // Get the database
        SQLiteDatabase db = this.database.getReadableDatabase();

        // Execute the query
        Cursor c = db.rawQuery(sql, null);

        // Guard clause - no major was found
        if(c.getCount() < 1) { return m;}

        // Move the cursor to the first object
        c.moveToFirst();

        // Fill in all major values
        m.setId(c.getInt(0));
        m.setName(c.getString(1));
        m.setPrefix(c.getString(2));

        // Close the cursor
        c.close();

        // close the database
        db.close();

        // return our value
        return m;
    }
    //---------------------------------------------------------------------------------------------
    public Major findByName(String name)
    {
        // First we create a new error object type
        Major m = new Major("", "");

        // Create the query
        String sql = "select * from " + this.tableName +
                " where name ='" + name + "';";

        // Get the database
        SQLiteDatabase db = this.database.getReadableDatabase();

        // Execute the query
        Cursor c = db.rawQuery(sql, null);

        // Guard clause - no major was found
        if(c.getCount() < 1) { return m; }

        // Move the cursor to the first object
        c.moveToFirst();

        // Fill in all major values
        m.setId(c.getInt(0));
        m.setName(c.getString(1));
        m.setPrefix(c.getString(2));

        // Close the cursor
        c.close();

        // close the database
        db.close();

        // return our value
        return m;
    }
    //---------------------------------------------------------------------------------------------
    public Major findByPrefix(String prefix)
    {
        // First we create a new error object type
        Major m = new Major("", "");

        // Create the query
        String sql = "select * from " + this.tableName +
                " where prefix ='" + prefix + "';";

        // Get the database
        SQLiteDatabase db = this.database.getReadableDatabase();

        // Execute the query
        Cursor c = db.rawQuery(sql, null);

        // Guard clause - no major was found
        if(c.getCount() < 1) { return m; }

        // Move the cursor to the first object
        c.moveToFirst();

        // Fill in all major values
        m.setId(c.getInt(0));
        m.setName(c.getString(1));
        m.setPrefix(c.getString(2));

        // Close the cursor
        c.close();

        // close the database
        db.close();

        // return our value
        return m;
    }
    //---------------------------------------------------------------------------------------------
    public void insert(String name, String prefix)
    {
        // Get the database
        SQLiteDatabase db = this.database.getWritableDatabase();

        // Create the inset string
        String sql = "insert into majors " +
                "(name, prefix) VALUES (" +
                name + "," + prefix + ");";

        // Execute insert
        db.execSQL(sql);
    }
    //---------------------------------------------------------------------------------------------
    public void insert(@NonNull Major m)
    {
        // Get the database
        SQLiteDatabase db = this.database.getWritableDatabase();

        // Create the inset string
        String sql = "insert into majors " +
                "(name, prefix) VALUES ('" +
                m.getName() + "','" + m.getPrefix() + "');";

        // Execute insert
        db.execSQL(sql);
    }
    //---------------------------------------------------------------------------------------------
    public boolean validate(@NonNull Major m)
    {
        // Create a new hashmap for errors
        HashMap<String, String> errors = new HashMap<String, String>();

        // First, validate the id
        if(m.getId() < 0)
        {
            errors.put("major id", "Id cannot be a negative number");
        }

        // Next, validate the name
        if(m.getName().isEmpty())
        {
            errors.put("major name", "Major name cannot be empty");
        }

        // Next, validate the prefix
        if(m.getPrefix().isEmpty())
        {
            errors.put("major prefix", "Major Prefix cannot be empty");
        }

        // Set the session errors
        Session.setErrors(errors);

        // return true if there are no errors
        return errors.isEmpty();
    }
    //---------------------------------------------------------------------------------------------

}
