package com.example.flashcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        // check to see if list is empty, then display saved card
        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }



        // Initialize background and text fields
        final TextView question =  (TextView) findViewById(R.id.flashcard_question);
        final TextView ans = (TextView) findViewById(R.id.flashcard_answer);
        final TextView choice1  = (TextView) findViewById(R.id.flashcard_choice1);
        final TextView choice2  = (TextView) findViewById(R.id.flashcard_choice2);
        final TextView choice3  = (TextView) findViewById(R.id.flashcard_choice3);
        final View background = findViewById(R.id.rootView);
        final Button btn = findViewById(R.id.toggleButton);
        final ImageView addBtn = (ImageView) findViewById(R.id.addBtn);
        final ImageView editBtn = (ImageView) findViewById(R.id.editBtn);
        final ImageView nextBtn = (ImageView) findViewById(R.id.nextBtn);


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

//        // click response for multiple choice text fields
//        choice1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                choice1.setBackgroundColor(RED);
//                choice3.setBackgroundColor(GREEN);
//            }
//        });
//
//        choice2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                choice2.setBackgroundColor(RED);
//                choice3.setBackgroundColor(GREEN);
//            }
//        });
//
//        choice3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                choice3.setBackgroundColor(GREEN);
//            }
//        });

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

        // make add button go to add activity
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);

                // turn multiple choice off by default
                choice1.setVisibility(View.INVISIBLE);
                choice2.setVisibility(View.INVISIBLE);
                choice3.setVisibility(View.INVISIBLE);
            }

        });

        // make edit button go to add activity
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from text fields
                String questionStr = question.getText().toString();
                String answerStr = ans.getText().toString();

                // create new intent, passing over the data
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("question", questionStr);
                intent.putExtra("answer", answerStr);
                MainActivity.this.startActivityForResult(intent, 200);

                // turn multiple choice off by default
                choice1.setVisibility(View.INVISIBLE);
                choice2.setVisibility(View.INVISIBLE);
                choice3.setVisibility(View.INVISIBLE);
            }
        });

        //next button
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
            }
        });
    }

    // method to get data from addCard activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 || requestCode == 200) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String question = data.getExtras().getString("string1"); // 'string1' needs to match the key we used when we put the string in the Intent
            String answer = data.getExtras().getString("string2");
            ((TextView)findViewById(R.id.flashcard_question)).setText(question);
            ((TextView)findViewById(R.id.flashcard_answer)).setText(answer);

            //save flashcard to database
            flashcardDatabase.insertCard(new Flashcard(question, answer));
            // update list of flashcards
            allFlashcards = flashcardDatabase.getAllCards();

        }
    }

    FlashcardDatabase flashcardDatabase;
    // holds list of flashcards
    List<Flashcard> allFlashcards;

    int currentCardDisplayedIndex = 0;



}
