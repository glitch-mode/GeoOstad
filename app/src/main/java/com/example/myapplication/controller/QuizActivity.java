package com.example.myapplication.controller;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;


public class QuizActivity extends AppCompatActivity {


    private FragmentManager mfragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mfragmentManager = getSupportFragmentManager();
        mfragmentManager
                .beginTransaction()
                .add(R.id.fragment_quiz_container, new QuizFragment())
                .commit();
    }

}