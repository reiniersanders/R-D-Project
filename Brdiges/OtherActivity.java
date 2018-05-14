package com.example.willemcoster.hashiwokakero;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class OtherActivity extends AppCompatActivity {

    static final String STATE_SCORE = "playerScore";
    static final String STATE_LEVEL = "playerLevel";
    int mCurrentScore, mCurrentLevel;

    TextView scoreView,levelView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otheractivity);

        scoreView = findViewById(R.id.scoreView);
        levelView = findViewById(R.id.levelView);

        if (savedInstanceState != null) {
            mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
            mCurrentLevel = savedInstanceState.getInt(STATE_LEVEL);
        } else {
            mCurrentScore = 0;
            mCurrentLevel = 1;
        }

        scoreView.setText(String.valueOf(mCurrentScore));
        levelView.setText(String.valueOf(mCurrentLevel));


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt(STATE_SCORE, mCurrentScore);
        savedInstanceState.putInt(STATE_LEVEL, mCurrentLevel);


        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void levelPlus(View v) {
        mCurrentLevel++;
        levelView.setText(String.valueOf(mCurrentLevel));

    }

    public void scorePlus(View v) {
        mCurrentScore++;
        scoreView.setText(String.valueOf(mCurrentScore));
    }
}
