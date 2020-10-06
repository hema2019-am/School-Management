package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ClassResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_class_result);
        setContentView(new TableMainLayout(this));
    }
}
