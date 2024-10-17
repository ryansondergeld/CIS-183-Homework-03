package com.example.homework03.Core;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.homework03.Models.Major;
import com.example.homework03.Models.Student;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper
{
    private static final String DB_NAME = "data.db";
    private static final String STUDENTS_TABLE = "students";
    private static final String MAJORS_TABLE = "majors";

    public Database(Context c)
    {
        // This will call the functionality of SQLiteOpen Helper and run the onCreate command
        super(c, DB_NAME, null, 6);
    }
    //---------------------------------------------------------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase db)
    {

        // Create the Majors table
        String sql;
        sql =   "create table " + MAJORS_TABLE + "(" +
                "id integer primary key autoincrement not null," +
                "name varchar(50)," +
                "prefix varchar(50)" +
                ");";
        db.execSQL(sql);

        // Create the Students table
        String sql2;
        sql2 =  "create table " + STUDENTS_TABLE + "(" +
                "id integer primary key not null," +
                "first_name varchar(50)," +
                "last_name varchar(50)," +
                "email varchar(50)," +
                "age integer," +
                "gpa real," +
                "major_id integer," +
                "foreign key (major_id) references " + MAJORS_TABLE +
                "(id)" +
                ");";
        db.execSQL(sql2);

    }
    //---------------------------------------------------------------------------------------------
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        // Drop tables
        db.execSQL("drop table if exists " + STUDENTS_TABLE + ";");
        db.execSQL("drop table if exists " + MAJORS_TABLE + ";");

        onCreate(db);
    }
    //---------------------------------------------------------------------------------------------
    public void initialize()
    {
        this.initMajors();
        this.initStudents();
    }
    //---------------------------------------------------------------------------------------------
    public void initMajors()
    {
        // Guard clause for Posts table
        if(numberOfRecordsInTable(MAJORS_TABLE) != 0) { return; }

        // Get the database
        SQLiteDatabase db = this.getWritableDatabase();

        // Create the inset string
        String sql = "insert into " + MAJORS_TABLE +
                "(name, prefix) VALUES (";

        // Values
        db.execSQL(sql + "'Accounting', 'ACCT');");
        db.execSQL(sql + "'Art', 'ART');");
        db.execSQL(sql + "'Biology', 'BIOL');");
        db.execSQL(sql + "'Business', 'BUS');");
        db.execSQL(sql + "'Chemistry', 'CHEM');");
        db.execSQL(sql + "'Communication', 'COM');");
        db.execSQL(sql + "'Computer Science', 'CS');");
        db.execSQL(sql + "'Dance', 'DNCE');");
        db.execSQL(sql + "'Economics', 'ECON');");
        db.execSQL(sql + "'Education', 'EDU');");
        db.execSQL(sql + "'English', 'ENG');");
        db.execSQL(sql + "'Engineering Technology', 'ETSC');");
        db.execSQL(sql + "'Film', 'FILM');");
        db.execSQL(sql + "'French', 'FR');");
        db.execSQL(sql + "'German', 'GERM');");
        db.execSQL(sql + "'History', 'HIST');");
        db.execSQL(sql + "'Humanities', 'HUM');");
        db.execSQL(sql + "'Interdisciplinary Studies', 'IDS');");
        db.execSQL(sql + "'Japanese', 'JAPN');");
        db.execSQL(sql + "'Law', 'LAW');");
        db.execSQL(sql + "'Mathematics', 'MATH');");
        db.execSQL(sql + "'Mechanical Engineering Technology', 'MET');");
        db.execSQL(sql + "'Physical Education', 'PE');");
        db.execSQL(sql + "'Physics', 'PHYS');");
        db.execSQL(sql + "'Political Science', 'POSC');");
        db.execSQL(sql + "'Psychology', 'PSY');");
        db.execSQL(sql + "'Science', 'SCI');");
        db.execSQL(sql + "'Spanish', 'SPAN');");
        db.execSQL(sql + "'Theater Arts', 'THAR');");

        // Close the database
        db.close();


    }
    //---------------------------------------------------------------------------------------------
    public void initStudents()
    {
        // Guard clause for Posts table
        if(numberOfRecordsInTable(STUDENTS_TABLE) != 0) { return; }

        // Get the database
        SQLiteDatabase db = this.getWritableDatabase();

        // Create the inset string
        String sql = "insert into " + STUDENTS_TABLE +
                "(id, first_name, last_name, email, age, gpa, major_id) VALUES (";

        // Values
        db.execSQL(sql + "100001, 'Dipper', 'Pines', 'dpines@mysteryshack.com', 12, 2.7, 2);");
        db.execSQL(sql + "100002, 'Mabel', 'Pines', 'mpines@mysteryshack.com', 12, 3.5, 1);");
        db.execSQL(sql + "100003, 'Stanley', 'Pines', 'spines@mysteryshack.com', 57, 1.8, 10);");
        db.execSQL(sql + "100004, 'Jesus', 'Ramierez', 'soos@mysteryshack.com', 23, 1.2, 12);");
        db.execSQL(sql + "100005, 'Wendy', 'Corduroy', 'wendy@mysteryshack.com', 15, 3.1, 11);");
        db.execSQL(sql + "100006, 'Stanford', 'Pines', 'stanpines@mysteryshack.com', 57, 4.0, 16);");
        db.execSQL(sql + "100007, 'Fiddleford', 'McGucket', 'fmcgucket@gobblewonker.com', 53, 3.7, 3);");
        db.execSQL(sql + "100008, 'Pacifica', 'Northwest', 'pacifica@northwestindustries.com', 12, 3.1, 4);");
        db.execSQL(sql + "100009, 'Robert', 'Valentino', 'rvalentino@quickstop.com', 16, 2.4, 7);");
        db.execSQL(sql + "100010, 'Daryl', 'Bubs', 'sheriff@gravityfallspd.com', 37, 2.8, 8);");
        db.execSQL(sql + "100011, 'Edwin', 'Durland', 'edurland@gravityfallspd.com', 34, 2.5, 9);");
        db.execSQL(sql + "100012, 'Tobias', 'Determined', 'toby@gfgossiper.com', 12, 3.5, 1);");
        db.execSQL(sql + "100013, 'William', 'Cipher', 'bill@weirdmageddon.com', 99, 4.0, 13);");
        db.execSQL(sql + "100014, 'Gideon', 'Gleeful', 'gideon@tentoftelepathy.com', 10, 3.2, 17);");
        db.execSQL(sql + "100015, 'Susan', 'Wentworth', 'susan@touristtrap.com', 29, 3.2, 21);");

        // Close the database
        db.close();
    }
    //---------------------------------------------------------------------------------------------
    public int numberOfRecordsInTable(String t)
    {
        // Get an instance of the readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Count how many records are in the table
        int n = (int) DatabaseUtils.queryNumEntries(db, t);

        // Close the database
        db.close();

        // Return the value
        return n;
    }
    //---------------------------------------------------------------------------------------------
}
