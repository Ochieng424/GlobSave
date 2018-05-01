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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    LinearLayoutCompat logIn_ParentLayout;
    TextInputLayout signInEmailWrap, signInPasswordWrap;
    TextInputEditText signInPassword, signInEmail;

    Button signUp, signIn;

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.LogInTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();

        logIn_ParentLayout = findViewById(R.id.logIn_ParentLayout);
        signInEmailWrap = findViewById(R.id.signInEmailWrap);
        signInPasswordWrap = findViewById(R.id.signInPasswordWrap);
        signInPassword = findViewById(R.id.signInPassword);
        signInEmail = findViewById(R.id.signInEmail);

        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.sign_up);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signInEmail.getText().toString().trim();
                String password = signInPassword.getText().toString();

                if (email.isEmpty())
                    signInEmailWrap.setError("Enter First Name");
                else {
                    signInEmailWrap.setError(null);
                    signInEmailWrap.setErrorEnabled(false);
                }

                if (!SignupActivity.isValidEmail(email))
                    signInEmailWrap.setError("Invalid email");
                else {
                    signInEmailWrap.setError(null);
                    signInEmailWrap.setErrorEnabled(false);
                }

                if (password.isEmpty())
                    signInPasswordWrap.setError("Enter Password");
                else {
                    signInPasswordWrap.setError(null);
                    signInPasswordWrap.setErrorEnabled(false);
                }

                if (password.length() < 6)
                    signInPasswordWrap.setError("minimum password length is 6");
                else {
                    signInPasswordWrap.setError(null);
                    signInPasswordWrap.setErrorEnabled(false);
                }

                if (!email.isEmpty()
                        && SignupActivity.isValidEmail(email)
                        && !password.isEmpty()
                        && password.length() >= 6) {

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        ActivityCompat.finishAffinity(LoginActivity.this);
                                    }else {
                                        Log.w("LoginActivity", "Failed to sign in user", task.getException());
                                        Snackbar.make(logIn_ParentLayout, "Invalid username or password", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // starts activity signup


                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser!=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            ActivityCompat.finishAffinity(LoginActivity.this);
        }
    }
}
