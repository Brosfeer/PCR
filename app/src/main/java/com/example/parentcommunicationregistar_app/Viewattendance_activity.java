package com.example.parentcommunicationregistar_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.parentcommunicationregistar_app.bean.ApplicationContext;
import com.example.parentcommunicationregistar_app.bean.AttendanceBean;
import com.example.parentcommunicationregistar_app.bean.StudentBean;
import com.example.parentcommunicationregistar_app.db.DBAdapter;

import java.util.ArrayList;

public class Viewattendance_activity extends AppCompatActivity {

    ArrayList<AttendanceBean> attendanceBeanList;
    private ListView listView ;
    private ArrayAdapter<String> listAdapter;
    DBAdapter dbAdapter = new DBAdapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewattendance_activity);
        listView=(ListView)findViewById(R.id.listview);
        final ArrayList<String> attendanceList = new ArrayList<String>();
        attendanceList.add("Id   | StudentName      | Status");

        attendanceBeanList=dbAdapter.getAllAttendanceByStudent();

        for(AttendanceBean attendanceBean : attendanceBeanList)
        {
            String users;
            String status = "";
                StudentBean studentBean =dbAdapter.getStudentById(attendanceBean.getAttendance_student_id());
                if(attendanceBean.getAttendance_status().equals("P"))
                {
                  status="Present";
                }
                else if(attendanceBean.getAttendance_status().equals("A"))
                {
                    status="Absent";
                }

                users = attendanceBean.getAttendance_student_id()+".  "+studentBean.getStudent_name()+","+studentBean.getStudent_class()+"         "+status;

            attendanceList.add(users);
            Log.d("users: ", users);

        }

        listAdapter = new ArrayAdapter<String>(this, R.layout.student_card, R.id.nametext, attendanceList);
        listView.setAdapter( listAdapter );
    }
}