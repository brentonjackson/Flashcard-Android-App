package com.example.flashcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        final ImageView cancelBtn = (ImageView) findViewById(R.id.cancelBtn);
        final ImageView saveBtn = (ImageView) findViewById(R.id.saveBtn);
        final EditText question = (EditText) findViewById(R.id.question);
        final EditText answer = (EditText) findViewById(R.id.answer);

        // make cancel button go to back to main activity
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCardActivity.this, MainActivity.class);
                AddCardActivity.this.startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        // make save button save and go to back to main activity
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get data from text fields
                String questionStr = question.getText().toString();
                String answerStr = answer.getText().toString();


                Intent data = new Intent(); // create a new Intent, this is where we will put our data
                data.putExtra("string1", questionStr); // puts question string into the Intent, with the key as 'string1'
                data.putExtra("string2", answerStr); // puts answer string into the Intent, with key 'string2'
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes this activity and pass data to the original activity that launched this activity
            }
        });

    }

    // method to get data from main activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == 200) {
            String current_question = getIntent().getStringExtra("question");
            String current_ans = getIntent().getStringExtra("answer");
            ((EditText)findViewById(R.id.question)).setText(current_question);
            ((EditText)findViewById(R.id.answer)).setText(current_ans);

        }
    }

    // Exit animation

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
