package com.example.ochieng_derrick.globsave.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ochieng_derrick.globsave.R;
import com.example.ochieng_derrick.globsave.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    LinearLayoutCompat signUp_parent;
    TextInputLayout txtFirstNameWrap, txtLastNameWrap, txtEmailWrap, txtPasswordWrap, txtConfirmPasswordWrap;
    TextInputEditText txtFirstName, txtLastName, txtEmail, txtPassword, txtConfirmPassword;


    Button log_in, btnSignUp;


    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Obtain the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();

        // Obtain the FirebaseFirestore instance.
        db = FirebaseFirestore.getInstance();

        signUp_parent = findViewById(R.id.signUp_ParentLayout);
        txtFirstNameWrap = findViewById(R.id.txtFirstNameWrap);
        txtLastNameWrap = findViewById(R.id.txtLastNameWrap);
        txtEmailWrap = findViewById(R.id.txtEmailWrap);
        txtPasswordWrap = findViewById(R.id.txtPasswordWrap);
        txtConfirmPasswordWrap = findViewById(R.id.txtConfirmPasswordWrap);

        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);

        btnSignUp = findViewById(R.id.btnSignUp);
        log_in = findViewById(R.id.log_in);

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstName = txtFirstName.getText().toString().trim();
                final String lastName = txtLastName.getText().toString().trim();
                final String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString();
                String confirmPassword = txtConfirmPassword.getText().toString();

                if (firstName.isEmpty())
                    txtFirstNameWrap.setError("Enter First Name");
                else {
                    txtFirstNameWrap.setError(null);
                    txtFirstNameWrap.setErrorEnabled(false);
                }

                if (lastName.isEmpty())
                    txtLastNameWrap.setError("Enter Last Name");
                else {
                    txtLastNameWrap.setError(null);
                    txtLastNameWrap.setErrorEnabled(false);
                }

                if (email.isEmpty())
                    txtEmailWrap.setError("Enter Email");
                else {
                    txtEmailWrap.setError(null);
                    txtEmailWrap.setErrorEnabled(false);
                }

                if (!isValidEmail(email))
                    txtEmailWrap.setError("Invalid email");
                else {
                    txtEmailWrap.setError(null);
                    txtEmailWrap.setErrorEnabled(false);
                }

                if (password.isEmpty())
                    txtPasswordWrap.setError("Enter Password");
                else {
                    txtPasswordWrap.setError(null);
                    txtPasswordWrap.setErrorEnabled(false);
                }

                if (password.length() < 6)
                    txtPasswordWrap.setError("minimum password length is 6");
                else {
                    txtPasswordWrap.setError(null);
                    txtPasswordWrap.setErrorEnabled(false);
                }

                if (!confirmPassword.equals(password))
                    txtConfirmPasswordWrap.setError("Passwords do not match");
                else {
                    txtConfirmPasswordWrap.setError(null);
                    txtConfirmPasswordWrap.setErrorEnabled(false);
                }

                if (!firstName.isEmpty()
                        && !lastName.isEmpty()
                        && !email.isEmpty()
                        && isValidEmail(email)
                        && !password.isEmpty()
                        && password.length() >= 6
                        && confirmPassword.equals(password)) {

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                        User user = new User(firebaseUser.getUid(), firstName, lastName, email);
                                        // Add new user to collection
                                        db.collection("users").document(firebaseUser.getUid())
                                                .set(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        startActivity(new Intent(
                                                                SignupActivity.this,
                                                                MainActivity.class));

                                                        // Clears previous activities on stack
                                                        ActivityCompat.finishAffinity(SignupActivity.this);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w("SignupActivity", "Failed to write user to database", e);
                                                        Snackbar.make(signUp_parent, "An error occurred..Try again", Snackbar.LENGTH_LONG).show();
                                                    }
                                                });
                                    } else {
                                        Log.w("SignupActivity", "Failed to create user", task.getException());
                                        Snackbar.make(signUp_parent, "An error occurred..Try again", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    public static boolean isValidEmail(String email) {
        final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
        return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }
}
