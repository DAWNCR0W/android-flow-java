package com.donghyeokseo.flow.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.Util;
import com.donghyeokseo.flow.service.NotificationService;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class MainActivity extends AppCompatActivity {

    @BindAnim(R.anim.fade_in1)
    Animation fadeIn1;
    @BindAnim(R.anim.fade_in2)
    Animation fadeIn2;
    @BindAnim(R.anim.fade_in3)
    Animation fadeIn3;
    @BindAnim(R.anim.fade_in4)
    Animation fadeIn4;
    @BindAnim(R.anim.fade_in5)
    Animation fadeIn5;

    @BindView(R.id.view_meal_button)
    Button viewMealBtn;
    @BindView(R.id.apply_out_go_button)
    Button viewApplyOutGoBtn;
    @BindView(R.id.apply_out_sleep_button)
    Button viewApplyOutSleepBtn;
    @BindView(R.id.out_check_button)
    Button viewOutCheckBtn;
    @BindView(R.id.relogin_button)
    Button reLoginBtn;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        Util.getDeviceNavigationBarHeigh(MainActivity.this);
    }

    @Override
    protected void onResume() {

        super.onResume();

        viewMealBtn.startAnimation(fadeIn1);
        viewApplyOutGoBtn.startAnimation(fadeIn2);
        viewApplyOutSleepBtn.startAnimation(fadeIn3);
        viewOutCheckBtn.startAnimation(fadeIn4);
        reLoginBtn.startAnimation(fadeIn5);
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
