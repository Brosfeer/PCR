package com.example.parentcommunicationregistar_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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

import com.example.parentcommunicationregistar_app.bean.FacultyBean;
import com.example.parentcommunicationregistar_app.db.DBAdapter;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class AddFactultyActivity extends AppCompatActivity implements Validator.ValidationListener {
    private CircleImageView profile_image;
    @NotEmpty
    @Length(min = 3, max = 10)
    private TextInputEditText name;
    @NotEmpty
    private TextInputEditText address;
    @NotEmpty
    private TextInputEditText phone;
    @NotEmpty
    private TextInputEditText qualif;

    @NotEmpty
    @Email
    private TextInputEditText email;

    @NotEmpty
    @Password
    private TextInputEditText password;

    private AppCompatButton btn_save;
    private Validator validator;
    private RadioButton male_btn, female_btn;
    String Gender = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_factulty);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });

        validator = new Validator(this);
        validator.setValidationListener(this);

        // Inflate the layout for this fragment
        name = (TextInputEditText) findViewById(R.id.name);
        phone = (TextInputEditText) findViewById(R.id.phone);
        address = (TextInputEditText) findViewById(R.id.address);
        qualif = (TextInputEditText) findViewById(R.id.qualif);
        email = (TextInputEditText) findViewById(R.id.email);
        password = (TextInputEditText) findViewById(R.id.password);

        male_btn = (RadioButton) findViewById(R.id.male_btn);
        female_btn = (RadioButton) findViewById(R.id.female_btn);

        btn_save = (AppCompatButton) findViewById(R.id.btn_save);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);


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


        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(AddFactultyActivity.this)
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

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();

            }
        });


    }


    @Override
    public void onValidationSucceeded() {
        String Name = name.getText().toString();
        String Address = address.getText().toString();
        String Phone = phone.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String Qualif = qualif.getText().toString();
        if (male_btn.isChecked()) {
            Gender = "Male";
        } else if (female_btn.isChecked()) {
            Gender = "Female";
        }

        FacultyBean facultyBean = new FacultyBean();
        facultyBean.setfaculty_name(Name);
        facultyBean.setfaculty_qualif(Qualif);
        facultyBean.setFaculty_mobilenumber(Phone);
        facultyBean.setFaculty_address(Address);
        facultyBean.setFaculty_Email(Email);
        facultyBean.setFaculty_password(Password);
        facultyBean.setFaculty_gender(Gender);


        DBAdapter dbAdapter = new DBAdapter(AddFactultyActivity.this);
        dbAdapter.addFaculty(facultyBean);

        Toast.makeText(AddFactultyActivity.this, "Faculty added successfully", Toast.LENGTH_SHORT).show();

        name.setText("");
        email.setText("");
        address.setText("");
        qualif.setText("");
        password.setText("");
        phone.setText("");


    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(AddFactultyActivity.this);
            // Display error messages
            if (view instanceof TextInputEditText) {
                ((TextInputEditText) view).setError(message);
            } else {
                Toast.makeText(AddFactultyActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }


}