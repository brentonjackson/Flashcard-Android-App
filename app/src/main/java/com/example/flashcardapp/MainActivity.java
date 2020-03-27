package com.example.flashcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        /*
        To view database: run/open emulator first!
        In Android studio terminal:
        Go to C:\Users\Brentonius\AppData\Local\Android\Sdk\platform-tools
        Enter: adb forward tcp:8080 tcp:8080
        Go to localhost:8080 in browser
         */

        // check to see if list is empty, then display saved card
        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }



        // Initialize background and text fields
        final TextView question =  (TextView) findViewById(R.id.flashcard_question);
        final TextView ans = (TextView) findViewById(R.id.flashcard_answer);
//        final TextView choice1  = (TextView) findViewById(R.id.flashcard_choice1);
//        final TextView choice2  = (TextView) findViewById(R.id.flashcard_choice2);
//        final TextView choice3  = (TextView) findViewById(R.id.flashcard_choice3);
        final View background = findViewById(R.id.rootView);
        final Button btn = findViewById(R.id.toggleButton);
        final ImageView addBtn = (ImageView) findViewById(R.id.addBtn);
        final ImageView editBtn = (ImageView) findViewById(R.id.editBtn);
        final ImageView deleteBtn = (ImageView) findViewById(R.id.deleteBtn);
        final ImageView nextBtn = (ImageView) findViewById(R.id.nextBtn);
        final ImageView prevBtn = (ImageView) findViewById(R.id.prevBtn);
        final TextView flashcardNum = (TextView) findViewById(R.id.flashcardNumber);


        // set initial flashcard number text and initial card
        flashcardNum.setText(currentCardDisplayedIndex+1 + " / " + allFlashcards.size() );

        // if no cards to go through
        if (allFlashcards.size() == 0) {
            question.setText("Add a card!");
            ans.setText("No answers yet buddy!");
            nextBtn.setVisibility(View.GONE);
            prevBtn.setVisibility(View.GONE);
            flashcardNum.setVisibility(View.GONE);
        }

        if (allFlashcards.size() > 0) {
            flashcardNum.setVisibility(View.VISIBLE);
        }

        if (allFlashcards.size() == 1) {
            nextBtn.setVisibility(View.GONE);
            prevBtn.setVisibility(View.GONE);
        }


        // click response for question text field
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (question.getVisibility() == View.VISIBLE) {
                    question.setVisibility(View.INVISIBLE);
                    ans.setVisibility(View.VISIBLE);

                }
                findViewById(R.id.flashcard_question).setCameraDistance(25000);
                findViewById(R.id.flashcard_answer).setCameraDistance(25000);
                question.animate()
                        .rotationY(90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        question.setVisibility(View.INVISIBLE);
                                        findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                                        // second quarter turn
                                        findViewById(R.id.flashcard_answer).setRotationY(-90);
                                        findViewById(R.id.flashcard_answer).animate()
                                                .rotationY(0)
                                                .setDuration(200)
                                                .start();
                                    }
                                }
                        ).start();

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

                // get the center for the clipping circle
                int cx = question.getWidth() / 2;
                int cy = question.getHeight() / 2;

                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                // create the animator for this view (the start radius is zero)
//                Animator anim = ViewAnimationUtils.createCircularReveal(question, cx, cy, 0f, finalRadius);
                ans.animate()
                        .rotationY(-90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        ans.setVisibility(View.INVISIBLE);
                                        question.setVisibility(View.VISIBLE);
                                        question.setRotationY(90);
                                        question.animate()
                                                .rotationY(0)
                                                .setDuration(200)
                                                .start();

                                    }
                                }
                        )
                        .start();

                // hide the question and show the answer to prepare for playing the animation!
//                question.setVisibility(View.INVISIBLE);
//                ans.setVisibility(View.VISIBLE);

//                anim.setDuration(500);
//                anim.start();
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
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (choice1.getVisibility() == View.VISIBLE) {
//                    choice1.setVisibility(View.INVISIBLE);
//                    choice2.setVisibility(View.INVISIBLE);
//                    choice3.setVisibility(View.INVISIBLE);
//                } else {
//                    choice1.setVisibility(View.VISIBLE);
//                    choice2.setVisibility(View.VISIBLE);
//                    choice3.setVisibility(View.VISIBLE);
//                }
//            }
//       });

        // reset everything when background clicked
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans.setVisibility(View.INVISIBLE);
                question.setVisibility(View.VISIBLE);
            }
        });

        // make delete button delete card from database
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardDatabase.deleteCard(question.getText().toString());
                // update database
                allFlashcards = flashcardDatabase.getAllCards();

                // advance our pointer index so we can show the next card
                if (currentCardDisplayedIndex > 0) {
                    currentCardDisplayedIndex--;
                } else {
                    currentCardDisplayedIndex++;
                }
                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                if (allFlashcards.size() > 0) {
                    question.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    ans.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    flashcardNum.setText(currentCardDisplayedIndex + 1 + " / " + allFlashcards.size());
                } else {
                    question.setText("Add a card!");
                    ans.setText("No answers yet buddy!");
                    nextBtn.setVisibility(View.GONE);
                    prevBtn.setVisibility(View.GONE);
                    flashcardNum.setVisibility(View.GONE);
                }
                if (allFlashcards.size() == 1) {
                    nextBtn.setVisibility(View.GONE);
                    prevBtn.setVisibility(View.GONE);
                }
            }
        });

        // make add button go to add activity
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                if (allFlashcards.size() > 0) {
                    flashcardNum.setVisibility(View.VISIBLE);
                }

                if (allFlashcards.size() > 1) {
                    nextBtn.setVisibility(View.VISIBLE);
                    prevBtn.setVisibility(View.VISIBLE);
                }


            }

        });

        // make edit button go to add activity
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from text fields
                String questionStr = question.getText().toString();
                String answerStr = ans.getText().toString();

                for (Flashcard card: allFlashcards) {
                    if (card.getQuestion() == questionStr) {
                        cardToEdit = card;
                    }
                }

                // create new intent, passing over the data
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("question", questionStr);
                intent.putExtra("answer", answerStr);
                MainActivity.this.startActivityForResult(intent, 200);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        //next button
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    // add animation
                    final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.slide_out_left);
                    final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.slide_in_right);

                    // Animation listener to chain animations to end of slide out
                    leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            // this method is called when the animation first starts
//                            question.setVisibility(View.INVISIBLE);
//                            ans.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // this method is called when the animation is finished playing
                            question.startAnimation(rightInAnim);
                            if (ans.getVisibility() == View.VISIBLE) {
                                ans.startAnimation(rightInAnim);
                            }
                            // set the question and answer TextViews with data from the database
                            question.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                            ans.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());


                            flashcardNum.setText(currentCardDisplayedIndex + 1 + " / " + allFlashcards.size());
                            // If answer is already toggled, toggle off and switch back to next question
//                            question.setVisibility(View.VISIBLE);
//                            ans.setVisibility(View.INVISIBLE);



                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // we don't need to worry about this method
                        }
                    });

                if (allFlashcards.size() > 2) {
                        // make next card random
                        Random rand = new Random();
                        int nextCard = rand.nextInt(allFlashcards.size());
                        if (nextCard == currentCardDisplayedIndex) {
                            nextCard = rand.nextInt(allFlashcards.size());
                        }
                        currentCardDisplayedIndex = nextCard;
                } else if (allFlashcards.size() <= 1){
                        nextBtn.setVisibility(View.GONE);
                        prevBtn.setVisibility(View.GONE);
                        currentCardDisplayedIndex = 0;
                } else {
                        currentCardDisplayedIndex++;
                        if (currentCardDisplayedIndex+1 > allFlashcards.size()) {
                        currentCardDisplayedIndex = 0;
                    }
                }

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list


                    question.startAnimation(leftOutAnim);
                if (ans.getVisibility() == View.VISIBLE) {
                    ans.startAnimation(leftOutAnim);
                }

            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add animation
                final Animation leftInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.slide_in_left);
                final Animation rightOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.slide_out_right);

                // Animation listener to chain animations to end of slide out
                rightOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        question.startAnimation(leftInAnim);
                        if (ans.getVisibility() == View.VISIBLE) {
                            ans.startAnimation(leftInAnim);
                        }
                        // set the question and answer TextViews with data from the database
                        ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                        ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                        flashcardNum.setText(currentCardDisplayedIndex+1 + " / " + allFlashcards.size() );
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });
                // decrement our pointer index so we can show the prev card
                currentCardDisplayedIndex--;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex < 0) {
                    currentCardDisplayedIndex = allFlashcards.size() - 1;
                }

                question.startAnimation(rightOutAnim);
                if (ans.getVisibility() == View.VISIBLE) {
                    ans.startAnimation(rightOutAnim);
                }
            }
        });
    }

    // method to get data from addCard activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String question = data.getExtras().getString("string1"); // 'string1' needs to match the key we used when we put the string in the Intent
            String answer = data.getExtras().getString("string2");
            ((TextView)findViewById(R.id.flashcard_question)).setText(question);
            ((TextView)findViewById(R.id.flashcard_answer)).setText(answer);

            //save flashcard to database
            flashcardDatabase.insertCard(new Flashcard(question, answer));
            // update list of flashcards
            allFlashcards = flashcardDatabase.getAllCards();

            if (allFlashcards.size() > 1) {
                findViewById(R.id.nextBtn).setVisibility(View.VISIBLE);
                findViewById(R.id.prevBtn).setVisibility(View.VISIBLE);
            }
            // update flashcard number
            ((TextView)findViewById(R.id.flashcardNumber)).setText(allFlashcards.size() + " / " + allFlashcards.size() );
            findViewById(R.id.flashcardNumber).setVisibility(View.VISIBLE);

        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            String question = data.getExtras().getString("string1"); // 'string1' needs to match the key we used when we put the string in the Intent
            String answer = data.getExtras().getString("string2");
            ((TextView)findViewById(R.id.flashcard_question)).setText(question);
            ((TextView)findViewById(R.id.flashcard_answer)).setText(answer);

            cardToEdit.setQuestion(question);
            cardToEdit.setAnswer(answer);
            flashcardDatabase.updateCard(cardToEdit);
        }
    }
    
    Flashcard cardToEdit;
    
    FlashcardDatabase flashcardDatabase;
    // holds list of flashcards
    List<Flashcard> allFlashcards;

    int currentCardDisplayedIndex = 0;
}
