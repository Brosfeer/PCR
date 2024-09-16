package com.example.parentcommunicationregistar_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.parentcommunicationregistar_app.bean.StudentBean;
import com.example.parentcommunicationregistar_app.db.DBAdapter;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class AddStudentActivity extends AppCompatActivity implements Validator.ValidationListener {

    private CircleImageView profile_image;
    @NotEmpty
    @Length(min = 3, max = 10)
    private TextInputEditText name;
    @NotEmpty
    private TextInputEditText address;
    @NotEmpty
    private TextInputEditText phone;
    @NotEmpty
    private TextInputEditText dob;

    private TextInputLayout dob_layout;
    private TextInputLayout class_layout;
    private AutoCompleteTextView class_tv;

    private AppCompatButton btn_save;
    private Validator validator;
    private RadioButton male_btn,female_btn;
    String Gender="";

    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    String selected_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        ActivityResultLauncher<Intent> galleryLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Uri uri = result.getData().getData();
                        // For example
                        profile_image.setImageURI(uri);

                    } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                        // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    }
                });


        validator = new Validator(this);
        validator.setValidationListener(this);

        name = (TextInputEditText) findViewById(R.id.name);
        phone =(TextInputEditText) findViewById(R.id.phone);
        address =(TextInputEditText) findViewById(R.id.address);
        dob =(TextInputEditText) findViewById(R.id.dob);
        male_btn = (RadioButton) findViewById(R.id.male_btn);
        female_btn=(RadioButton)findViewById(R.id.female_btn);

        dob_layout=(TextInputLayout)findViewById(R.id.dob_layout);
        class_layout=(TextInputLayout)findViewById(R.id.class_layout);
        class_tv=(AutoCompleteTextView)findViewById(R.id.class_tv);

        btn_save=(AppCompatButton)findViewById(R.id.btn_save);
        profile_image=(CircleImageView)findViewById(R.id.profile_image);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(AddStudentActivity.this)
                        .crop()
                        .cropOval()
                        .maxResultSize(1080, 1080, true)
                        .createIntentFromDialog(new Function1() {
                            public Object invoke(Object var1) {
                                this.invoke((Intent) var1);
                                return Unit.INSTANCE;
                            }

                            public final void invoke(@NotNull Intent it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                galleryLauncher.launch(it);
                            }


                        });

            }
        });


        MaterialDatePicker.Builder builder =MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Your Date:");
        builder.setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR);
        final MaterialDatePicker materialDatePicker=builder.build();


        dob_layout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");

            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(selection -> dob.setText(materialDatePicker.getHeaderText()));


        class_tv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selected_class=parent.getItemAtPosition(position).toString();
            }
        });
        arrayList =new ArrayList<>();
        arrayList.add("Class 1");
        arrayList.add("Class 2");
        arrayList.add("Class 3");
        arrayList.add("Class 4");
        arrayList.add("Class 5");
        arrayList.add("Class 6");
        arrayList.add("Class 7");
        arrayList.add("Class 8");
        arrayList.add("Class 9");
        arrayList.add("Class 10");

        arrayAdapter =new ArrayAdapter<>(AddStudentActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arrayList);
        class_tv.setAdapter(arrayAdapter);
        class_tv.setThreshold(1);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();

            }
        });

    }




    @Override
    public void onValidationSucceeded() {
        String Name=name.getText().toString();
        String Address = address.getText().toString();
        String Phone = phone.getText().toString();
        String Dob = dob.getText().toString();
        if (male_btn.isChecked()) { Gender="Male"; } else if(female_btn.isChecked()) { Gender="Female"; }

        StudentBean studentBean = new StudentBean();

        studentBean.setStudent_name(Name);
        studentBean.setStudent_dob(Dob);
        studentBean.setStudent_mobilenumber(Phone);
        studentBean.setStudent_address(Address);
        studentBean.setStudent_class(selected_class);
        studentBean.setStudent_gender(Gender);


        DBAdapter dbAdapter= new DBAdapter(AddStudentActivity.this);
        dbAdapter.addStudent(studentBean);

        Toast.makeText(AddStudentActivity.this,"Student Added Succefully!", Toast.LENGTH_SHORT).show();

        name.setText("");
        phone.setText("");
        dob.setText("");
        address.setText("");
        class_tv.setText("");

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(AddStudentActivity.this);
            // Display error messages
            if (view instanceof TextInputEditText) {
                ((TextInputEditText) view).setError(message);
            } else {
                Toast.makeText(AddStudentActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

}