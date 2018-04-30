package com.donghyeokseo.flow.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.Util;

import butterknife.ButterKnife;
import butterknife.OnClick;

public final class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        Util.getDeviceNavigationBarHeigh(MainActivity.this);
    }

    //버튼 이벤트
    @OnClick(R.id.view_meal_button)
    public void OnViewMealBtnClicked(View view) {
        Intent mealIntent = new Intent(MainActivity.this, MealActivity.class);
        startActivity(mealIntent);
    }

    @OnClick(R.id.apply_out_go_button)
    public void OnApplyOutGoBtnClicked(View view) {
        Intent i = new Intent(MainActivity.this, OutActivity.class);
        i.putExtra("IsSleep", false);
        startActivity(i);
    }

    @OnClick(R.id.apply_out_sleep_button)
    public void OnApplyOutSleepButtonClicked(View view) {
        Intent i = new Intent(MainActivity.this, OutActivity.class);
        i.putExtra("IsSleep", true);
        startActivity(i);
    }

    @OnClick(R.id.out_check_button)
    public void OnOutCheckButtonClicked(View view) {
        Intent i = new Intent(MainActivity.this, OutCheckActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.relogin_button)
    public void OnReLoginButtonClicked(View view) {
        Intent i = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(i);

        getSharedPreferences("Login", Activity.MODE_PRIVATE)
                .edit()
                .putBoolean("isReLogin", true)
                .apply();
        finish();
    }
}
