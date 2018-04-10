package com.donghyeokseo.flow.activity;

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
        ButterKnife.bind(this);
        Util.getDeviceNavigationBarHeigh(this);
    }

    //버튼 이벤트
    @OnClick(R.id.view_meal_button)
    public void OnViewMealBtnClicked(View view) {
        Intent mealIntent = new Intent(this, MealActivity.class);
        startActivity(mealIntent);
    }

    @OnClick(R.id.apply_out_button)
    public void OnApplyOutBtnClicked(View view) {
//        Intent outIntent new Intent(this, )
    }

    @OnClick(R.id.login_button)
    public void OnLoginBtnClicked(View view) {
        Intent loginIntent = new Intent(this, SignInActivity.class);
        startActivity(loginIntent);
    }
}
