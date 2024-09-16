package com.example.parentcommunicationregistar_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.parentcommunicationregistar_app.bean.AttendanceBean;
import com.example.parentcommunicationregistar_app.bean.AttendanceSessionBean;
import com.example.parentcommunicationregistar_app.bean.StudentBean;
import com.example.parentcommunicationregistar_app.db.DBAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class take_attendance extends AppCompatActivity {
    private TextInputLayout class_layout;
    private AutoCompleteTextView class_tv;
    int sessionId = 0;
    String status = "";
    Button attendanceSubmit;
    private ListView student_list;
    private MaterialButton select_btn;
    private ArrayAdapter<String> listAdapter;
    ArrayList<StudentBean> studentBeanList;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    String selected_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takeattendance);
        DBAdapter dbAdapter = new DBAdapter(this);
        // Inflate the layout for this fragment
        class_layout = (TextInputLayout) findViewById(R.id.class_layout);
        class_tv = (AutoCompleteTextView) findViewById(R.id.class_tv);
        student_list = (ListView) findViewById(R.id.student_list);
        select_btn = (MaterialButton) findViewById(R.id.select_btn);


        class_tv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selected_class = parent.getItemAtPosition(position).toString();
            }
        });
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            arrayList.add("Class " + i);
        }

        arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayList);
        class_tv.setAdapter(arrayAdapter);
        class_tv.setThreshold(1);

        final ArrayList<String> studentList = new ArrayList<String>();

        studentBeanList = dbAdapter.getAllStudent();

        for (StudentBean studentBean : studentBeanList) {
            String users = studentBean.getStudent_name() + "," + studentBean.getStudent_class();
            studentList.add(users);
            Log.d("users: ", users);
        }

        select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentList.clear();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(new Date());

                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();

                attendanceSessionBean.setAttendance_session_faculty_id(2);
                attendanceSessionBean.setAttendance_session_class(selected_class);
                attendanceSessionBean.setAttendance_session_date(date);


                studentBeanList = dbAdapter.getAllStudentByClass(selected_class);

                for (StudentBean studentBean : studentBeanList) {
                    String users = studentBean.getStudent_name() + "," + studentBean.getStudent_class();
                    studentList.add(users);
                    listAdapter.notifyDataSetChanged();
                    Log.d("users: ", users);
                }
            }
        });

        listAdapter = new ArrayAdapter<String>(getApplication(), R.layout.student_card, R.id.nametext, studentList);
        student_list.setAdapter(listAdapter);

        student_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                parent.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                //arg0.setBackgroundColor(234567);
                view.setBackgroundColor(334455);
                final StudentBean studentBean = studentBeanList.get(position);
                final Dialog dialog = new Dialog(take_attendance.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
                dialog.setContentView(R.layout.attendence_dialog);
                // set title and cancelable
                RadioGroup radioGroup;
                RadioButton present;
                RadioButton absent;
                radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
                present = (RadioButton) dialog.findViewById(R.id.PresentradioButton);
                absent = (RadioButton) dialog.findViewById(R.id.AbsentradioButton);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.PresentradioButton) {

                            status = "P";
                        } else if (checkedId == R.id.AbsentradioButton) {

                            status = "A";
                        } else {
                        }
                    }
                });

                attendanceSubmit = (Button) dialog.findViewById(R.id.attendanceSubmitButton);
                attendanceSubmit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        AttendanceBean attendanceBean = new AttendanceBean();

                        attendanceBean.setAttendance_session_id(sessionId);
                        attendanceBean.setAttendance_student_id(studentBean.getStudent_id());
                        attendanceBean.setAttendance_status(status);

                        DBAdapter dbAdapter = new DBAdapter(take_attendance.this);
                        dbAdapter.addNewAttendance(attendanceBean);

                        dialog.dismiss();

                    }
                });

                dialog.setCancelable(true);
                dialog.show();
            }
        });

    }
}