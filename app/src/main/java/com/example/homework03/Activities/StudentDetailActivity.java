package com.example.homework03.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homework03.Core.MajorsTable;
import com.example.homework03.Core.Session;
import com.example.homework03.Core.StudentsTable;
import com.example.homework03.Models.Major;
import com.example.homework03.Models.Student;
import com.example.homework03.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentDetailActivity extends AppCompatActivity
        implements View.OnFocusChangeListener, View.OnClickListener, DialogInterface.OnShowListener
{
    // Main Page control items
    EditText id;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText gpa;
    EditText age;
    AutoCompleteTextView major;
    ArrayList<Major> majors;
    ArrayAdapter<Major> majorAdapter;
    ImageButton backButton;
    ImageButton deleteButton;
    Intent gotoMain;
    ExtendedFloatingActionButton saveButton;

    // Dialog for adding a major
    Dialog addMajorDialog;
    Button addMajorDialogYesButton;
    Button addMajorDialogNoButton;
    TextView addMajorDialogQuestion;

    // Dialog for adding a prefix
    Dialog addPrefixDialog;
    Button addPrefixDialogSaveButton;
    Button addPrefixDialogCancelButton;
    TextView addPrefixDialogQuestion;
    TextView addPrefixDialogError;
    EditText addPrefixDialogPrefix;

    // Dialog for Delete student
    Dialog deleteStudentDialog;
    Button deleteStudentDialogYesButton;
    Button deleteStudentDialogNoButton;

    // Error TextViews
    TextView idError;
    TextView firstNameError;
    TextView lastNameError;
    TextView emailError;
    TextView gpaError;
    TextView ageError;
    TextView majorError;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_detail);

        // Create Dialogs
        addMajorDialog = new Dialog(this);
        addMajorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addMajorDialog.setCancelable(true);
        addMajorDialog.setContentView(R.layout.dialog_add_major);

        addPrefixDialog = new Dialog(this);
        addPrefixDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addPrefixDialog.setCancelable(true);
        addPrefixDialog.setContentView(R.layout.dialog_add_prefix);

        deleteStudentDialog = new Dialog(this);
        deleteStudentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        deleteStudentDialog.setCancelable(true);
        deleteStudentDialog.setContentView(R.layout.dialog_delete_student);

        // Map Controls
        this.id = findViewById(R.id.activity_student_insert_et_id);
        this.firstName = findViewById(R.id.activity_student_insert_et_first_name);
        this.lastName = findViewById(R.id.activity_student_insert_et_last_name);
        this.email = findViewById(R.id.activity_student_insert_et_email);
        this.gpa = findViewById(R.id.activity_student_insert_et_gpa);
        this.age = findViewById(R.id.activity_student_insert_et_age);
        this.major = findViewById(R.id.activity_student_detail_et_major);
        this.backButton = findViewById(R.id.activity_student_detail_btn_back);
        this.deleteButton = findViewById(R.id.activity_student_detail_btn_delete);
        this.saveButton = findViewById(R.id.activity_student_insert_btn_save);
        this.idError = findViewById(R.id.activity_student_insert_tv_id_error);
        this.firstNameError = findViewById(R.id.activity_student_insert_tv_first_name_error);
        this.lastNameError = findViewById(R.id.activity_student_insert_tv_last_name_error);
        this.emailError = findViewById(R.id.activity_student_insert_tv_email_error);
        this.ageError = findViewById(R.id.activity_student_insert_tv_age_error);
        this.gpaError = findViewById(R.id.activity_student_insert_tv_gpa_error);
        this.majorError = findViewById(R.id.activity_student_insert_tv_major_error);

        // Map add major Dialog Controls
        this.addMajorDialogYesButton = addMajorDialog.findViewById(R.id.dialog_add_major_btn_yes);
        this.addMajorDialogNoButton = addMajorDialog.findViewById(R.id.dialog_add_major_btn_no);
        this.addMajorDialogQuestion = addMajorDialog.findViewById(R.id.dialog_add_major_tv_question);

        // Map add prefix dialog controls
        this.addPrefixDialogSaveButton = addPrefixDialog.findViewById(R.id.dialog_add_prefix_btn_save);
        this.addPrefixDialogCancelButton = addPrefixDialog.findViewById(R.id.dialog_add_prefix_btn_cancel);
        this.addPrefixDialogQuestion = addPrefixDialog.findViewById(R.id.dialog_add_prefix_tv_question);
        this.addPrefixDialogError = addPrefixDialog.findViewById(R.id.dialog_add_prefix_tv_error);
        this.addPrefixDialogPrefix = addPrefixDialog.findViewById(R.id.dialog_add_prefix_et_prefix);

        // Map delete student dialog
        this.deleteStudentDialogYesButton = deleteStudentDialog.findViewById(R.id.dialog_delete_student_btn_yes);
        this.deleteStudentDialogNoButton = deleteStudentDialog.findViewById(R.id.dialog_delete_student_btn_no);

        // get the majors table
        MajorsTable mt = new MajorsTable(this);

        // Get all of the majors
        majors = mt.findAll();

        // Create adapter for majors
        majorAdapter = new ArrayAdapter<Major>(this,
                android.R.layout.simple_dropdown_item_1line,
                majors);

        // Set the adapter
        major.setAdapter(majorAdapter);

        // Set Events
        major.setOnFocusChangeListener(this);
        this.backButton.setOnClickListener(this);
        this.saveButton.setOnClickListener(this);
        this.deleteButton.setOnClickListener(this);

        // Set add Major Dialog Events
        addMajorDialogYesButton.setOnClickListener(this);
        addMajorDialogNoButton.setOnClickListener(this);
        addMajorDialog.setOnShowListener(this);

        // Set add Prefix Dialog events
        addPrefixDialogSaveButton.setOnClickListener(this);
        addPrefixDialogCancelButton.setOnClickListener(this);
        addPrefixDialog.setOnShowListener(this);

        // Set Delete student Dialog events
        deleteStudentDialogYesButton.setOnClickListener(this);
        deleteStudentDialogNoButton.setOnClickListener(this);

        // Reset all errors
        resetErrors();

        // Here we meed to load different items depending on insert or update
        if(Session.getCommand().matches("update")) { initUpdate(); }
        else { initInsert(); }

    }
    //---------------------------------------------------------------------------------------------
    @Override
    public void onFocusChange(View v, boolean b)
    {
        if(v.getId() == R.id.activity_student_detail_et_major) { majorFocusChange(); }
    }
    //---------------------------------------------------------------------------------------------
    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.activity_student_detail_btn_back)
        {
            backButtonPressed();
        }
        else if (v.getId() == R.id.activity_student_insert_btn_save)
        {
            saveButtonPressed();
        }
        else if (v.getId() == R.id.activity_student_detail_btn_delete)
        {
            deleteButtonPressed();
        }
        else if (v.getId() == R.id.dialog_add_major_btn_yes)
        {
            addMajorDialogYesButtonPressed();
        }
        else if (v.getId() == R.id.dialog_add_major_btn_no)
        {
            addMajorDialogNoButtonPressed();
        }
        else if (v.getId() == R.id.dialog_add_prefix_btn_save)
        {
            addPrefixDialogSaveButtonPressed();
        }
        else if (v.getId() == R.id.dialog_add_prefix_btn_cancel)
        {
            addPrefixDialogCancelButtonPressed();
        }
        else if(v.getId() == R.id.dialog_delete_student_btn_yes)
        {
            deleteStudentDialogYesButtonPressed();
        }
        else if(v.getId() == R.id.dialog_delete_student_btn_no)
        {
            deleteStudentDialogNoButtonPressed();
        }
    }
    //---------------------------------------------------------------------------------------------
    @Override
    public void onShow(DialogInterface di)
    {
        String m = "The major " + major.getText() + " does not exist.  Would you like to add it?";
        addMajorDialogQuestion.setText(m);

        String n = "Add prefix for the major " + major.getText() + ":";
        addPrefixDialogQuestion.setText(n);
        addPrefixDialogError.setVisibility(View.INVISIBLE);
    }
    //---------------------------------------------------------------------------------------------
    public void addMajorDialogYesButtonPressed()
    {
        addMajorDialog.dismiss();
        addPrefixDialog.show();
    }
    //---------------------------------------------------------------------------------------------
    public void addMajorDialogNoButtonPressed()
    {
        addMajorDialog.dismiss();
    }
    //---------------------------------------------------------------------------------------------
    public void addPrefixDialogSaveButtonPressed()
    {
        // Here we need to see if this is a valid entry
        String check = addPrefixDialogPrefix.getText().toString();

        // Must have a valid prefix to continue
        if(invalidPrefix(check)) { return; }

        // Passed error checking - create new major object and insert
        Major m = new Major();

        // Grab name
        m.setName(major.getText().toString());

        // Grab prefix
        m.setPrefix(addPrefixDialogPrefix.getText().toString());

        // Create a Majors Table object to insert into
        MajorsTable mt = new MajorsTable(this);

        // Insert our Major which gives it an ID
        mt.insert(m);

        // Get the new major object with the new id
        m = mt.findByName(m.getName());

        // Set our Major to the session variable
        // ------------[ do I need to set the session value!!?? ] ------------
        Session.setMajor(m);

        // Close the dialog
        addPrefixDialog.dismiss();

        // Call the insert student function
        addStudent(m);
    }
    //---------------------------------------------------------------------------------------------
    public void addPrefixDialogCancelButtonPressed()
    {
        addPrefixDialog.dismiss();
    }
    //---------------------------------------------------------------------------------------------
    public void addStudent(Major m)
    {
        // NOTE: We can only get here if the major is now valid and we can add it

        // Pull the student from session data
        Student s = Session.getStudent();

        // Set the student major data
        s.setMajor(m);

        // Create a students table to insert the student
        StudentsTable st = new StudentsTable(this);

        // Insert the student or add the student
        if(Session.getCommand().matches("insert")) { st.insert(s); }
        else { st.update(s); }

        // Since we changed the data, we want to make sure the students are refreshed
        Session.setStudents(null);

        // Use the back button pressed function to return to the main page
        backButtonPressed();

    }
    //---------------------------------------------------------------------------------------------
    public void backButtonPressed()
    {
        // Create the intent
        gotoMain = new Intent(StudentDetailActivity.this, MainActivity.class);

        // Go back to Main
        startActivity(gotoMain);

    }
    //---------------------------------------------------------------------------------------------
    public void checkMajor(Student s)
    {
        // NOTE: We can only get here if the rest of student data passed, so put student in Session
        Session.setStudent(s);

        // create a majors table
        MajorsTable mt = new MajorsTable(this);

        // grab a majors object
        Major m = mt.findByName(major.getText().toString());

        // Prompt for new Major if it doesn't exist
        if(m.getName().isEmpty()) { addMajorDialog.show(); }
        else { addStudent(m); }

    }
    //---------------------------------------------------------------------------------------------
    private void deleteButtonPressed()
    {
        deleteStudentDialog.show();
    }
    //---------------------------------------------------------------------------------------------
    private void deleteStudentDialogNoButtonPressed()
    {
        deleteStudentDialog.dismiss();
    }
    //---------------------------------------------------------------------------------------------
    private void deleteStudentDialogYesButtonPressed()
    {
        // We must have a valid ID here - so just create a blank student with the ID
        Student s = new Student();

        // Set the student ID
        s.setId(Integer.parseInt(this.id.getText().toString()));

        // Create a students table instance
        StudentsTable st = new StudentsTable(this);

        // Delete the student
        st.delete(s);

        // Dismiss the dialog
        deleteStudentDialog.dismiss();

        // Since we changed the data, we want to make sure the students are refreshed
        Session.setStudents(null);

        // Use the go back button
        backButtonPressed();

    }
    //---------------------------------------------------------------------------------------------
    private void disableEditText(EditText et)
    {
        et.setFocusable(false);
        et.setEnabled(false);
        et.setCursorVisible(false);
        et.setKeyListener(null);
        et.setBackgroundColor(Color.TRANSPARENT);
    }
    //---------------------------------------------------------------------------------------------
    public void initInsert()
    {
        // Remove / Disable the Delete button
        deleteButton.setVisibility(View.INVISIBLE);

        // Create a student table object
        StudentsTable st = new StudentsTable(this);

        // Get the next id
        int nextId = st.getNextId();

        // set the ID value
        this.id.setText(String.valueOf(nextId));

        // Disable editing
        disableEditText(this.id);

        // Set the focus on the age
        age.requestFocus();
    }
    //---------------------------------------------------------------------------------------------
    public void initUpdate()
    {
        // Get the student Session info
        Student s = Session.getStudent();
        Major m = s.getMajor();

        // Set text of controls
        this.id.setText(String.valueOf(s.getId()));
        this.firstName.setText(s.getFirstName());
        this.lastName.setText(s.getLastName());
        this.email.setText(s.getEmail());
        this.gpa.setText(String.valueOf(s.getGpa()));
        this.age.setText(String.valueOf(s.getAge()));
        this.major.setText(m.getName());

        // Disable editing the student id
        disableEditText(this.id);
    }
    //---------------------------------------------------------------------------------------------
    public boolean invalidPrefix(String check)
    {
        // Create a major table object for reference
        MajorsTable mt = new MajorsTable(this);

        // Grab the major object from the table by prefix
        Major m = mt.findByPrefix(check);

        // Error handling for new prefix
        if(check.length() < 2)
        {
            addPrefixDialogError.setText("Length must be 2-4 letters");
            addPrefixDialogError.setVisibility(View.VISIBLE);
            return true;
        }
        else if (check.length() > 4)
        {
            addPrefixDialogError.setText("Length must be 2-4 letters");
            addPrefixDialogError.setVisibility(View.VISIBLE);
            return true;
        }
        else if(check.matches(".*\\d.*"))
        {
            addPrefixDialogError.setText("Prefix can not contain numbers");
            addPrefixDialogError.setVisibility(View.VISIBLE);
            return true;
        }
        else if (!m.getPrefix().isEmpty())
        {
            addPrefixDialogError.setText("Prefix already exists for another major!");
            addPrefixDialogError.setVisibility(View.VISIBLE);
            return true;
        }
        else
        {
            return false;
        }
    }
    //---------------------------------------------------------------------------------------------
    public void majorFocusChange()
    {
        // Handle Major AutoCompleteTextEdit Event
        this.major.showDropDown();
    }
    //---------------------------------------------------------------------------------------------
    public void saveButtonPressed()
    {
        // Create a student with all of the data
        Student s = new Student();

        // Grab all of the data from the textboxes
        // Grab ID if it exists - parse error if string is empty
        if(!this.id.getText().toString().isEmpty())
        {
            s.setId(Integer.parseInt(this.id.getText().toString()));
        }
        s.setFirstName(this.firstName.getText().toString());
        s.setLastName(this.lastName.getText().toString());
        s.setEmail(this.email.getText().toString());

        // Grab ID if it exists - parse error if string is empty
        if(!this.age.getText().toString().isEmpty())
        {
            s.setAge(Integer.parseInt(this.age.getText().toString()));
        }

        // Grab ID if it exists - parse error if string is empty
        if(!this.gpa.getText().toString().isEmpty())
        {
            s.setGpa(Float.parseFloat(this.gpa.getText().toString()));
        }

        // Create a major table to find the major
        MajorsTable mt = new MajorsTable(this);

        // grab the major if it exists
        Major m = mt.findByName(major.getText().toString());

        // Set the major
        s.setMajor(m);

        // If the data is valid, insert the item.  Otherwise, show errors.
        if(validEntry(s)) { checkMajor(s); }
        else { showErrors(); }

    }
    //---------------------------------------------------------------------------------------------
    public void resetErrors()
    {
        this.idError.setText("");
        this.idError.setVisibility(View.INVISIBLE);
        this.firstNameError.setText("");
        this.firstNameError.setVisibility(View.INVISIBLE);
        this.lastNameError.setText("");
        this.lastNameError.setVisibility(View.INVISIBLE);
        this.emailError.setText("");
        this.emailError.setVisibility(View.INVISIBLE);
        this.gpaError.setText("");
        this.gpaError.setVisibility(View.INVISIBLE);
        this.ageError.setText("");
        this.ageError.setVisibility(View.INVISIBLE);
        this.majorError.setText("");
        this.majorError.setVisibility(View.INVISIBLE);
    }
    //---------------------------------------------------------------------------------------------
    public void showErrors()
    {
        // Get Session Errors
        HashMap<String, String> errors = Session.getErrors();

        // Reset all errors before starting
        resetErrors();

        // Check ID
        String id = errors.get("id");
        if(id != null)
        {
            idError.setText(id);
            idError.setVisibility(View.VISIBLE);
        }

        // Check First Name
        String first = errors.get("first name");
        if(first != null)
        {
            firstNameError.setText(first);
            firstNameError.setVisibility(View.VISIBLE);
        }

        // Check Last Name
        String last = errors.get("last name");
        if(last != null)
        {
            lastNameError.setText(last);
            lastNameError.setVisibility(View.VISIBLE);
        }

        // Check Email
        String email = errors.get("email");
        if(email != null)
        {
            emailError.setText(email);
            emailError.setVisibility(View.VISIBLE);
        }

        String gpa = errors.get("gpa");
        if(gpa != null)
        {
            gpaError.setText(gpa);
            gpaError.setVisibility(View.VISIBLE);
        }

        // Check Age
        String age = errors.get("age");
        if(age != null)
        {
            ageError.setText(age);
            ageError.setVisibility(View.VISIBLE);
        }

        // Check Major
        if(major.getText().toString().isEmpty())
        {
            majorError.setText("Major cannot be blank!");
            majorError.setVisibility(View.VISIBLE);
        }
    }
    //---------------------------------------------------------------------------------------------
    public boolean validEntry(Student s)
    {

        // Create a student table
        StudentsTable st = new StudentsTable(this);

        // Validate an Update differently from an insert - but default to insert
        if(Session.getCommand().matches("update"))
        {
            return st.validateUpdate(s) && !major.getText().toString().isEmpty();
        }

        // Return insert by default
        return st.validateInsert(s) && !major.getText().toString().isEmpty();

    }
    //---------------------------------------------------------------------------------------------
}