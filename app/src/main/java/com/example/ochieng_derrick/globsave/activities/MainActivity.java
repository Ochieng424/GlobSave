package com.example.ochieng_derrick.globsave.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ochieng_derrick.globsave.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }
}
