package com.donghyeokseo.flow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.donghyeokseo.flow.R;

public final class MainActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
    }

    public final void goMealIntent(View view) {
        Intent i = new Intent(this, MealActivity.class);
        this.startActivity(i);
    }
}
