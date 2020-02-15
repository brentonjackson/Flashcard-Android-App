package com.example.flashcardapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize background and two text fields
        final TextView question = (TextView) findViewById(R.id.flashcard_question);
        final TextView ans = (TextView) findViewById(R.id.flashcard_answer);
        final View background = findViewById(R.id.rootView);

        // click response for question text field
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question.getVisibility() == View.VISIBLE) {
                    question.setVisibility(View.INVISIBLE);
                    ans.setVisibility(View.VISIBLE);
                }
            }
        });

        // click response for answer text field
        ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ans.getVisibility() == View.VISIBLE) {
                    ans.setVisibility(View.INVISIBLE);
                    question.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
