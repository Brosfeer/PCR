package com.example.parentcommunicationregistar_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.parentcommunicationregistar_app.bean.StudentBean;
import com.example.parentcommunicationregistar_app.db.DBAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    private TextInputLayout class_layout;
    private AutoCompleteTextView class_tv;

    private ListView student_list;
    FloatingActionButton fab;
    private MaterialButton select_btn;
    private ArrayAdapter<String> listAdapter;
    ArrayList<StudentBean> studentBeanList;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    String selected_class;

    final ArrayList<String> studentList = new ArrayList<>();
    DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        dbAdapter = new DBAdapter(StudentActivity.this);
        // Inflate the layout for this fragment

        class_layout = (TextInputLayout) findViewById(R.id.class_layout);
        class_tv = (AutoCompleteTextView) findViewById(R.id.class_tv);
        student_list = (ListView) findViewById(R.id.student_list);
        select_btn = (MaterialButton) findViewById(R.id.select_btn);


        class_tv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selected_class = parent.getItemAtPosition(position).toString();
                getAllStudent();
            }
        });
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            arrayList.add("Class " + i);
        }


        arrayAdapter = new ArrayAdapter<>(StudentActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayList);
        class_tv.setAdapter(arrayAdapter);
        class_tv.setThreshold(1);


        studentBeanList = dbAdapter.getAllStudent();
        for (StudentBean studentBean : studentBeanList) {
            String users = studentBean.getStudent_name() + "," + studentBean.getStudent_class();
            studentList.add(users);
            Log.d("users: ", users);
        }

        select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllStudent();
            }
        });


        listAdapter = new ArrayAdapter<String>(StudentActivity.this, R.layout.student_card, R.id.nametext, studentList);
        student_list.setAdapter(listAdapter);

        student_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StudentActivity.this);

                alertDialogBuilder.setMessage("Do you want to delete this student?" + parent.getItemAtPosition(position).toString());
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        studentList.remove(position);
                        listAdapter.notifyDataSetChanged();
                        listAdapter.notifyDataSetInvalidated();

                        dbAdapter.deleteStudent(studentBeanList.get(position).getStudent_id());
                        studentList.clear();
                        studentBeanList = dbAdapter.getAllStudentByClass(selected_class);

                        for (StudentBean studentBean : studentBeanList) {
                            String users = studentBean.getStudent_name() + "," + studentBean.getStudent_class();
                            studentList.add(users);
                            Log.d("users: ", users);

                        }
                    }

                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // cancel the alert box and put a Toast to the user
                        dialog.cancel();
                        Toast.makeText(StudentActivity.this, "You choose cancel",
                                Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                // show alert
                alertDialog.show();

                return false;
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, AddStudentActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getAllStudent() {
        studentList.clear();
        listAdapter.notifyDataSetChanged();
        studentBeanList = dbAdapter.getAllStudentByClass(selected_class);
        for (StudentBean studentBean : studentBeanList) {
            String users = studentBean.getStudent_name() + "," + studentBean.getStudent_class();
            studentList.add(users);
            listAdapter.notifyDataSetChanged();
            Log.d("users: ", users);
        }
    }
}