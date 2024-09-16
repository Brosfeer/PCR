package com.example.parentcommunicationregistar_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Faculty_Activity extends AppCompatActivity {
CardView Attendance_cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);

        Attendance_cv=(CardView)findViewById(R.id.attendance_cv);




    }

    public void take_attendance(View view) {
        Intent myIntent = new Intent(this, take_attendance.class);
        this.startActivity(myIntent);
    }

    public void view_att(View view) {
        Intent myIntent = new Intent(this, Viewattendance_activity.class);
        this.startActivity(myIntent);

    }

    public void logout(View view) {
        Intent myIntent = new Intent(this, Login_activity.class);
        this.startActivity(myIntent);
    }
}