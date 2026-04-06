package com.example.mindmatters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mindmatters.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.mindmatters.classes.User;
import com.example.mindmatters.classes.AvailableSlot;
import com.example.mindmatters.classes.Counsellor;
import com.example.mindmatters.classes.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Registration activity that creates Firebase Auth accounts and stores the matching app user record.
 * Outstanding issues: registration still seeds only very basic counsellor profile and schedule defaults.
 */
public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private CollectionReference usersRef;
    TextInputEditText editName;
    TextInputEditText editEmail;
    TextInputEditText editPassword;
    TextInputEditText editConfirmPassword;
    Button registerButton;
    Button backButton;
    RadioGroup chooseRole;

    @Override
    // Initializes the registration screen and its actions.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        //initializing objects
        editName = findViewById(R.id.fullname);
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        editConfirmPassword = findViewById(R.id.confirm_password);
        registerButton = findViewById(R.id.register_button);
        backButton = findViewById(R.id.back_button);
        chooseRole = findViewById(R.id.choose_role);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        usersRef = db.collection("users");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, confirmPassword, fullname;
                fullname = editName.getText().toString();
                email = editEmail.getText().toString().trim();
                password = editPassword.getText().toString();
                confirmPassword = editConfirmPassword.getText().toString();

                //if empty
                if (TextUtils.isEmpty(fullname))
                {
                    Toast.makeText(RegistrationActivity.this, "Enter full name.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(RegistrationActivity.this, "Enter email.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(RegistrationActivity.this, "Enter password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(confirmPassword))
                {
                    Toast.makeText(RegistrationActivity.this, "Confirm Password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //check if password and confirm password are same
                if (!password.equals(confirmPassword))
                {
                    Toast.makeText(RegistrationActivity.this, "Passwords don't match.", Toast.LENGTH_SHORT).show();
                    return;
                }
                //so user doesnt press sign in multiple times
                Toast.makeText(RegistrationActivity.this, "Creating account...", Toast.LENGTH_SHORT).show();
                //create user, add to db
                //code from firebase docs, edited according to use
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("RegistrationActivity", "createUserWithEmail:success");
                                    FirebaseUser firebaseUser = auth.getCurrentUser();
                                    if (firebaseUser != null) {
                                        // create new user
                                        String type;
                                        if (chooseRole.getCheckedRadioButtonId() == R.id.radio_student) {
                                            type = "student";
                                        } else {
                                            type = "counsellor";
                                        }
                                        User user;
                                        if ("counsellor".equals(type)) {
                                            Counsellor counsellor = new Counsellor(firebaseUser.getUid(), fullname, email);
                                            counsellor.setBio("Counsellor profile setup in progress.");
                                            counsellor.setSpeciality("Student Wellbeing");
                                            counsellor.setYearsExperience(1);
                                            counsellor.setSupportsOnline(true);
                                            counsellor.setSupportsInPerson(true);
                                            counsellor.setAvailableSlots(buildDefaultCounsellorSlots());
                                            user = counsellor;
                                        } else {
                                            Student student = new Student(firebaseUser.getUid(), fullname, email);
                                            user = student;
                                        }

                                        // save user to db
                                        usersRef.document(firebaseUser.getUid()).set(user).addOnSuccessListener(unused -> {
                                                    Toast.makeText(RegistrationActivity.this, "Account created successfully! Log in to your account.", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(RegistrationActivity.this, "Failed to save user data.", Toast.LENGTH_SHORT).show();
                                                    Log.w("RegistrationActivity", "Error saving user", e);
                                                });
                                    }
                                } else {
                                    Log.w("RegistrationActivity", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Builds default sample slots for a new counsellor account.
    private List<AvailableSlot> buildDefaultCounsellorSlots() {
        List<AvailableSlot> defaultSlots = new ArrayList<>();
        defaultSlots.add(new AvailableSlot("TUESDAY", "1:00 PM", "2:00 PM"));
        defaultSlots.add(new AvailableSlot("TUESDAY", "6:00 PM", "7:00 PM"));
        defaultSlots.add(new AvailableSlot("FRIDAY", "12:00 PM", "1:00 PM"));
        return defaultSlots;
    }
}
