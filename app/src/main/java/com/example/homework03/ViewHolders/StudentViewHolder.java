package com.example.homework03.ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework03.Interfaces.StudentClickListener;
import com.example.homework03.R;

public class StudentViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener
{
    public TextView name;
    public TextView id;
    public TextView major;
    public TextView gpa;
    public ImageButton details;
    public StudentClickListener click;

    public StudentViewHolder(@NonNull View itemView, StudentClickListener cl)
    {
        super(itemView);

        // Map controls and initialize
        this.name = itemView.findViewById(R.id.item_student_adapter_tv_name);
        this.id = itemView.findViewById(R.id.item_student_adapter_tv_id);
        this.major = itemView.findViewById(R.id.item_student_adpater_tv_major);
        this.gpa = itemView.findViewById(R.id.item_student_adapter_tv_gpa);
        this.click = cl;
        this.details = itemView.findViewById(R.id.item_student_adapter_btn_info);

        // Set any events
        this.details.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        // Do nothing if null
        if(click == null) { return; }

        // Get the position
        int p = getAdapterPosition();

        // if for some reason we don't have a position, exit
        if (p == RecyclerView.NO_POSITION) { return; }

        // Call the on item click function
        click.onItemClick(v, p);
    }
}
