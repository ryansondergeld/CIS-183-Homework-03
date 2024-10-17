package com.example.homework03.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework03.Adapters.StudentAdapter;
import com.example.homework03.Core.Database;
import com.example.homework03.Core.MajorsTable;
import com.example.homework03.Core.Session;
import com.example.homework03.Interfaces.StudentClickListener;
import com.example.homework03.Models.Major;
import com.example.homework03.Models.Student;
import com.example.homework03.Core.StudentsTable;
import com.example.homework03.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
    implements StudentClickListener, View.OnClickListener, DialogInterface.OnShowListener
{
    StudentAdapter adapter;
    RecyclerView rv;
    Intent gotoStudentInsertActivity;
    ExtendedFloatingActionButton fab;
    ImageButton showFilterButton;

    ArrayList<Student> students;
    ArrayList<Major> majors;
    ArrayAdapter<Major> majorAdapter;

    // Filter Dialog
    Dialog filterDialog;
    Button filterDialogApplyButton;
    Button filterDialogCancelButton;
    ImageButton filterDialogResetButton;
    EditText filterDialogGreaterThan;
    EditText filterDialogLessThan;
    EditText filterDialogFirstName;
    EditText filterDialogLastName;
    EditText filterDialogId;
    AutoCompleteTextView filterDialogMajor;
    Spinner filterDialogOrderBy;
    Spinner filterDialogOrder;

    //---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // We have to initialize the database in case we choose to change it
        Database d = new Database(this);
        d.initialize();

        // Create filter dialog
        filterDialog = new Dialog(this);
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filterDialog.setCancelable(true);
        filterDialog.setContentView(R.layout.dialog_filter_students);

        // Map Controls
        this.rv = findViewById(R.id.activity_main_rv);
        this.fab = findViewById(R.id.activity_main_btn_insert);
        this.showFilterButton = findViewById(R.id.activity_main_btn_search);
        this.filterDialogApplyButton = filterDialog.findViewById(R.id.dialog_filter_students_btn_apply);
        this.filterDialogCancelButton = filterDialog.findViewById(R.id.dialog_filter_students_btn_cancel);
        this.filterDialogResetButton = filterDialog.findViewById(R.id.dialog_filter_students_btn_reset);
        this.filterDialogOrderBy = filterDialog.findViewById(R.id.dialog_filter_students_sp_order_by);
        this.filterDialogOrder = filterDialog.findViewById(R.id.dialog_filter_students_sp_order);
        this.filterDialogFirstName = filterDialog.findViewById(R.id.dialog_filter_students_et_first_name);
        this.filterDialogLastName = filterDialog.findViewById(R.id.dialog_filter_students_et_last_name);
        this.filterDialogMajor = filterDialog.findViewById(R.id.dialog_filter_students_et_major);
        this.filterDialogGreaterThan = filterDialog.findViewById(R.id.dialog_filter_students_et_greater_than);
        this.filterDialogLessThan = filterDialog.findViewById(R.id.dialog_filter_students_et_less_than);
        this.filterDialogId = filterDialog.findViewById(R.id.dialog_filter_students_et_id);

        // Access our majors table
        MajorsTable mt = new MajorsTable(this);

        // Get all of the majors
        majors = mt.findAll();

        // Create adapter for majors
        majorAdapter = new ArrayAdapter<Major>(this,
                android.R.layout.simple_dropdown_item_1line,
                majors);

        // Set the major adaptor for the textview
        filterDialogMajor.setAdapter(majorAdapter);

        // Check for Session Data
        ArrayList<Student> check = Session.getStudents();

        // Initialize filters
        initFilters();

        // If session data is null, create a new list of students
        if(check != null) { students = Session.getStudents(); }
        else { students = this.getStudents(); }

        // Set the RecycleView with adapter
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(this, students, this);
        rv.setAdapter(adapter);

        // Set events
        this.fab.setOnClickListener(this);
        this.showFilterButton.setOnClickListener(this);
        this.filterDialogApplyButton.setOnClickListener(this);
        this.filterDialogCancelButton.setOnClickListener(this);
        this.filterDialogResetButton.setOnClickListener(this);
    }
    //---------------------------------------------------------------------------------------------
    @Override
    public void onItemClick(View v, int p)
    {
        // Get the Student Object clicked
        Student s = adapter.getItem(p);

        // Set the session variable
        Session.setStudent(s);

        // Set our command to update
        Session.setCommand("update");

        // Create the intent
        this.gotoStudentInsertActivity = new Intent(MainActivity.this,
                StudentDetailActivity.class);

        // Go to the student update / info page
        startActivity(gotoStudentInsertActivity);

    }
    //---------------------------------------------------------------------------------------------
    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.activity_main_btn_insert) { fabPressed(); }
        else if (v.getId() == R.id.activity_main_btn_search) { searchPressed(); }
        else if (v.getId() == R.id.dialog_filter_students_btn_apply) { filterDialogApplyButtonPressed(); }
        else if (v.getId() == R.id.dialog_filter_students_btn_cancel) { filterDialogCancelButtonPressed(); }
        else if (v.getId() == R.id.dialog_filter_students_btn_reset) { filterDialogResetButtonPressed(); }
    }
    //---------------------------------------------------------------------------------------------
    public void fabPressed()
    {
        // FAB clicked means our command should be new
        Session.setCommand("insert");

        // Create the intent
        this.gotoStudentInsertActivity = new Intent(MainActivity.this,
                StudentDetailActivity.class);

        // Go to the student update / info page
        startActivity(gotoStudentInsertActivity);
    }
    //---------------------------------------------------------------------------------------------
    public void filterDialogResetButtonPressed()
    {
        // Reset all filter values
        filterDialogOrder.setSelection(0);
        filterDialogOrderBy.setSelection(0);
        filterDialogFirstName.setText("");
        filterDialogLastName.setText("");
        filterDialogMajor.setText("");
        filterDialogGreaterThan.setText("0.0");
        filterDialogLessThan.setText("4.0");
        filterDialogId.setText("");

        // Call the apply pressed
        filterDialogApplyButtonPressed();
    }
    //---------------------------------------------------------------------------------------------
    public ArrayList<Student> getStudents()
    {
        // Get Students table
        StudentsTable st = new StudentsTable(this);

        HashMap<String, String> filters = Session.getFilters();

        if(filters == null) { return st.findAll(); }
        else { return st.findWhere(filters); }
    }
    //---------------------------------------------------------------------------------------------
    public void initFilters()
    {
        // Get the Session data
        HashMap<String, String> filter = Session.getFilters();

        // If the filters are empty, do nothing
        if(filter == null) { return; }

        // Filters aren't empty here - so we need a for each on the filter data
        for(String key : filter.keySet())
        {
            // Get the value
            String value = filter.get(key);

            // check if the value is empty - if so, move to the next item
            if(value.isEmpty()) { continue; }

            // Depending on the key, add to our query
            if(key.equals("first_name"))
            {
                filterDialogFirstName.setText(value.substring(1, value.length()-1));
            }
            else if (key.equals("last_name"))
            {
                filterDialogLastName.setText(value.substring(1, value.length()-1));
            }
            else if (key.equals("major_id"))
            {
                // Create a Majors Table
                MajorsTable mt = new MajorsTable(this);

                // Get the major
                Major m = mt.findById(Integer.parseInt(value));

                // Set the text
                filterDialogMajor.setText(m.getName());
            }
            else if (key.equals("greater_than"))
            {
                filterDialogGreaterThan.setText(value);
            }
            else if (key.equals("less_than"))
            {
                filterDialogLessThan.setText(value);
            }
        }

        // Check order by
        String orderBy = Session.getOrderBy();
        String orderType = Session.getOrderType();

        if(orderBy != null)
        {
            // Find the option that matches what we have
            for(int i=0; i < filterDialogOrderBy.getCount(); i++)
            {
                String v = filterDialogOrderBy.getItemAtPosition(i).toString();
                if(v.equalsIgnoreCase(orderBy))
                {
                    filterDialogOrderBy.setSelection(i);
                    break;
                }
            }
        }

        if(orderType != null)
        {
            // Find the option that matches what we have
            for(int i=0; i < filterDialogOrder.getCount(); i++)
            {
                String v = filterDialogOrder.getItemAtPosition(i).toString();
                if(v.equalsIgnoreCase(orderType))
                {
                    filterDialogOrder.setSelection(i);
                    break;
                }
            }
        }

    }
    //---------------------------------------------------------------------------------------------
    public void searchPressed()
    {
        filterDialog.show();
    }
    //---------------------------------------------------------------------------------------------
    public void filterDialogApplyButtonPressed()
    {

        // Clear the students
        students.clear();

        // Create the students table to query
        StudentsTable st = new StudentsTable(this);

        // Set the order by
        st.setOrderColumn(filterDialogOrderBy.getSelectedItem().toString());

        // Set the order Type
        st.setOrderType(filterDialogOrder.getSelectedItem().toString());

        // Create a where hashmap
        HashMap<String, String> filter = new HashMap<String, String>();

        // Grab values from the text boxes
        String firstName = filterDialogFirstName.getText().toString();
        String lastName = filterDialogLastName.getText().toString();
        String greaterThan = filterDialogGreaterThan.getText().toString();
        String lessThan = filterDialogLessThan.getText().toString();
        String id = filterDialogId.getText().toString();

        MajorsTable mt = new MajorsTable(this);
        Major m = mt.findByName(filterDialogMajor.getText().toString());

        // If the values aren't empty, add them to the hash map
        if(!firstName.isEmpty()) { filter.put("first_name", "%" + firstName + "%"); }
        if(!lastName.isEmpty()) { filter.put("last_name", "%" + lastName + "%"); }
        if(m.getId() != -1) { filter.put("major_id", String.valueOf(m.getId())); }
        if(!greaterThan.isEmpty()) { filter.put("greater_than", greaterThan); }
        if(!lessThan.isEmpty()) { filter.put("less_than", lessThan); }
        if(!id.isEmpty()) { filter.put("id", id); }

        // If the hashmap is empty, call find all, otherwise call find where
        if(filter.isEmpty()) { students.addAll(st.findAll()); }
        else { students.addAll(st.findWhere(filter)); }

        // Apply data to session variable
        Session.setStudents(students);
        Session.setFilters(filter);
        Session.setOrderBy(filterDialogOrderBy.getSelectedItem().toString());
        Session.setOrderType(filterDialogOrder.getSelectedItem().toString());

        // Notify Adapter
        adapter.notifyDataSetChanged();

        // Dismiss the dialog
        filterDialog.dismiss();

    }
    //---------------------------------------------------------------------------------------------
    public void filterDialogCancelButtonPressed()
    {
        filterDialog.dismiss();
    }
    //---------------------------------------------------------------------------------------------
    @Override
    public void onShow(DialogInterface dialogInterface)
    {

    }
    //---------------------------------------------------------------------------------------------
}