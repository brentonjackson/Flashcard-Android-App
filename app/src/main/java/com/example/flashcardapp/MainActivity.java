package com.example.flashcardapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize background and text fields
        final TextView question = (TextView) findViewById(R.id.flashcard_question);
        final TextView choice1  = (TextView) findViewById(R.id.flashcard_choice1);
        final TextView choice2  = (TextView) findViewById(R.id.flashcard_choice2);
        final TextView choice3  = (TextView) findViewById(R.id.flashcard_choice3);
        final TextView ans = (TextView) findViewById(R.id.flashcard_answer);
        final View background = findViewById(R.id.rootView);
        final Button btn = findViewById(R.id.toggleButton);


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

        // click response for correct answer text field
        ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ans.getVisibility() == View.VISIBLE) {
                    ans.setVisibility(View.INVISIBLE);
                    question.setVisibility(View.VISIBLE);
                }
            }
        });

        // click response for multiple choice text fields
        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice1.setBackgroundColor(RED);
                choice3.setBackgroundColor(GREEN);
            }
        });

        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice2.setBackgroundColor(RED);
                choice3.setBackgroundColor(GREEN);
            }
        });

        choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice3.setBackgroundColor(GREEN);
            }
        });

        // click response when toggle button clicked
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choice1.getVisibility() == View.VISIBLE) {
                    choice1.setVisibility(View.INVISIBLE);
                    choice2.setVisibility(View.INVISIBLE);
                    choice3.setVisibility(View.INVISIBLE);
                } else {
                    choice1.setVisibility(View.VISIBLE);
                    choice2.setVisibility(View.VISIBLE);
                    choice3.setVisibility(View.VISIBLE);
                }
            }
       });

        // reset everything when background clicked
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setVisibility(View.VISIBLE);
                ans.setVisibility(View.INVISIBLE);
                choice1.setBackgroundColor(getResources().getColor(R.color.lightYellow));
                choice2.setBackgroundColor(getResources().getColor(R.color.lightYellow));
                choice3.setBackgroundColor(getResources().getColor(R.color.lightYellow));
            }
        });
    }
}
