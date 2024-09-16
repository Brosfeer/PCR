package com.example.parentcommunicationregistar_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {


    CardView student_cv, faculty_cv, attendance_cv, logout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        student_cv = (CardView) root.findViewById(R.id.student_cv);
        faculty_cv = (CardView) root.findViewById(R.id.faculty_cv);
        attendance_cv = (CardView) root.findViewById(R.id.attendance_cv);
        logout = (CardView) root.findViewById(R.id.logout);

        student_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), StudentActivity.class);
                requireContext().startActivity(intent);

            }
        });
        faculty_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), AdminFacultyActivity.class);
                requireContext().startActivity(intent);

            }
        });
        attendance_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(), Viewattendance_activity.class);
                requireContext().startActivity(myIntent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(), Login_activity.class);
                getContext().startActivity(myIntent);
            }
        });
        return root;
    }

}
