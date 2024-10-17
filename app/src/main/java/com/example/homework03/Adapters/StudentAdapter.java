package com.example.homework03.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework03.Interfaces.StudentClickListener;
import com.example.homework03.Models.Major;
import com.example.homework03.Models.Student;
import com.example.homework03.R;
import com.example.homework03.ViewHolders.StudentViewHolder;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder>
{
    private ArrayList<Student> students;
    private LayoutInflater inflater;
    private StudentClickListener studentClickListener;

    public StudentAdapter(Context c, ArrayList<Student> students, StudentClickListener cl)
    {
        this.inflater = LayoutInflater.from(c);
        this.students = students;
        this.studentClickListener = cl;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // Inflate
        View v = this.inflater.inflate(R.layout.item_student_adapter, parent, false);

        // Return the view to the view holder
        return new StudentViewHolder(v, this.studentClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder h, int p)
    {
        // Grab the Student
        Student s = students.get(p);

        // Grab the major
        Major m = s.getMajor();

        // Set the text views or other items in the layout
        h.name.setText(s.getFirstName() + " " + s.getLastName());
        h.id.setText(String.valueOf(s.getId()));
        h.major.setText(m.getName());
        h.gpa.setText(String.valueOf(s.getGpa()));

    }

    @Override
    public int getItemCount() { return students.size(); }

    public Student getItem(int i) { return students.get(i);}
}
