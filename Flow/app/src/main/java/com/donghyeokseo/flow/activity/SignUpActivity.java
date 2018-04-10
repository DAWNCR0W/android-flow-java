package com.donghyeokseo.flow.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.donghyeokseo.flow.R;

import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }
}
