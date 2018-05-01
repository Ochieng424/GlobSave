package com.example.ochieng_derrick.globsave;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;

import static com.example.ochieng_derrick.globsave.SignupActivity.isValidEmail;

public class LoginActivity extends AppCompatActivity {
    LinearLayoutCompat logIn_ParentLayout;
    TextInputLayout signInEmailWrap, signInPasswordWrap;
    TextInputEditText signInPassword, signInEmail;

    Button signUp, signIn;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.LogInTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

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

                if (!isValidEmail(email))
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
                        && isValidEmail(email)
                        && !password.isEmpty()
                        && password.length() >= 6) {

                    Snackbar.make(logIn_ParentLayout, "All is well", Snackbar.LENGTH_LONG).show();
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
}
