package com.example.willemcoster.hashiwokakero;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Select extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
    }

    public void level1(View v) {
        Intent intent = new Intent(this, OtherActivity.class); startActivity(intent);
    }

    public void level2(View v) {
        Intent intent = new Intent(this, OtherActivity.class); startActivity(intent);
    }

    public void level3(View v) {
        Intent intent = new Intent(this, OtherActivity.class); startActivity(intent);
    }

}
