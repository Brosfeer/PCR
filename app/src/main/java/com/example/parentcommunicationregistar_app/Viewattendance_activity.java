package com.example.parentcommunicationregistar_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parentcommunicationregistar_app.bean.AttendanceBean;
import com.example.parentcommunicationregistar_app.db.DBAdapter;

import java.util.ArrayList;

public class Viewattendance_activity extends AppCompatActivity {

    public static final int REQUEST_CALL_PERMISSION = 1;
    private ListView listView;
    private AttendanceAdapter attendanceAdapter;
    private ArrayList<AttendanceBean> attendanceBeanList;
    private DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewattendance_activity);

        listView = findViewById(R.id.listview);
        dbAdapter = new DBAdapter(this);
        attendanceBeanList = dbAdapter.getAllAttendanceByStudent();

        // Check and request permission before setting up the adapter
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        } else {
            setupAdapter();
        }
    }

    private void setupAdapter() {
        attendanceAdapter = new AttendanceAdapter(this, attendanceBeanList);
        listView.setAdapter(attendanceAdapter);
    }

    // Handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, setup the adapter
                setupAdapter();
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Permission denied for phone calls", Toast.LENGTH_SHORT).show();
                setupAdapter(); // You can still set up the adapter, but without calling functionality
            }
        }
    }
}
