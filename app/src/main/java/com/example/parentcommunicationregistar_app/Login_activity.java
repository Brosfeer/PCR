package com.example.parentcommunicationregistar_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.parentcommunicationregistar_app.bean.ApplicationContext;
import com.example.parentcommunicationregistar_app.bean.FacultyBean;
import com.example.parentcommunicationregistar_app.db.DBAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.ArrayList;
import java.util.List;

public class Login_activity extends AppCompatActivity implements Validator.ValidationListener {
    @NotEmpty
    @Email
    private TextInputEditText email;
    @NotEmpty
    @Password
    private TextInputEditText password;

    private TextInputLayout class_layout;
    @NotEmpty
    private AutoCompleteTextView class_tv;

    private AppCompatButton btn_login;
    private Validator validator;

    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    String user_role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        class_layout=(TextInputLayout)findViewById(R.id.class_layout);
        class_tv=(AutoCompleteTextView)findViewById(R.id.class_tv);
        email=(TextInputEditText) findViewById(R.id.email);
        password=(TextInputEditText) findViewById(R.id.password);

        btn_login=(AppCompatButton)findViewById(R.id.btn_login);


        validator = new Validator(this);
        validator.setValidationListener(this);


        class_tv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                user_role=parent.getItemAtPosition(position).toString();
            }
        });
        arrayList =new ArrayList<>();
        arrayList.add("Admin");
        arrayList.add("Faculty");

        arrayAdapter =new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,arrayList);
        class_tv.setAdapter(arrayAdapter);
        class_tv.setThreshold(1);

    }
    @Override
    public void onValidationSucceeded() {
        if(user_role.equals("Admin"))
        {

            String user_name = email.getText().toString();
            String pass_word = password.getText().toString();


                if(user_name.equals("admin@gmail.com") & pass_word.equals("admin123")){
                    Intent intent =new Intent(Login_activity.this,Admin_activity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                }
            }


        else if(user_role.equals("Faculty"))
        {

            String user_name = email.getText().toString();
            String pass_word = password.getText().toString();

            DBAdapter dbAdapter = new DBAdapter(Login_activity.this);
            FacultyBean facultyBean = dbAdapter.validateFaculty(user_name, pass_word);

            if(facultyBean!=null)
            {
                Intent intent = new Intent(Login_activity.this,Faculty_Activity.class);
                startActivity(intent);
                //((ApplicationContext)Login_activity.this.getApplicationContext()).setFacultyBean(facultyBean);
                new ApplicationContext().setFacultyBean(facultyBean);
                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Please Select Your Account Type!", Toast.LENGTH_SHORT).show();
        }
    }


    public void login_onclick(View view) {

            validator.validate();
    }



    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages
            if (view instanceof TextInputEditText) {
                ((TextInputEditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }


}