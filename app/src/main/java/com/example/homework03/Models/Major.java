package com.example.homework03.Models;

import androidx.annotation.NonNull;

public class Major
{
    private int id;
    private String name;
    private String prefix;
    //---------------------------------------------------------------------------------------------
    public Major(String name, String prefix)
    {
        this.id = -1;
        this.name = name;
        this.prefix = prefix;
    }
    //---------------------------------------------------------------------------------------------
    public Major()
    {
        this.id = -1;
        this.name = "";
        this.prefix = "";
    }
    //---------------------------------------------------------------------------------------------
    @NonNull
    @Override
    public String toString() {return name;}
    //---------------------------------------------------------------------------------------------
    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getPrefix() { return this.prefix; }
    //---------------------------------------------------------------------------------------------
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrefix(String prefix) { this.prefix = prefix; }

}
