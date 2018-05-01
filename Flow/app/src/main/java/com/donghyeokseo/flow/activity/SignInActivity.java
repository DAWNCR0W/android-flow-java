package com.donghyeokseo.flow.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.database.DatabaseHelper;
import com.donghyeokseo.flow.network.RetrofitApi;
import com.donghyeokseo.flow.network.interfaces.SignInService;
import com.donghyeokseo.flow.network.request.signin.Request;
import com.donghyeokseo.flow.network.response.signin.Response;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

import static com.donghyeokseo.flow.Util.encryption;
import static com.donghyeokseo.flow.Util.isSchoolEmail;

public final class SignInActivity extends AppCompatActivity {

    SignInService signInService;

    @BindView(R.id.login_email_editText)
    EditText emailTv;
    @BindView(R.id.login_password_editText)
    EditText passwordTv;
    @BindView(R.id.auto_login_checkBox)
    CheckBox autoLoginCheckbox;
    @BindView(R.id.login_progressBar)
    ProgressBar progressBar;

    private SharedPreferences pref;



    @Override
    protected void onStop() {

        Log.e("서동혁", "Main : OnStop()");

        super.onStop();

        pref.edit().putBoolean("isReLogin", false).apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e("서동혁", "Main : OnCreate()");

        signInService = new RetrofitApi(SignInActivity.this).getSignInService();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);
        autoLoginCheckbox.setChecked(pref.getBoolean("autoLogin", false));
        emailTv.setText(pref.getString("id", ""));
        passwordTv.setText(pref.getString("pw", ""));

        if (autoLoginCheckbox.isChecked() && !pref.getBoolean("isReLogin", false)) {

            progressBar.setVisibility(View.VISIBLE);

            login();
        }

    }

    @OnClick(R.id.login_submit_button)
    public void OnLoginSubmitBtnClicked(View view) {

        login();
    }

    private void login() {

        String email = emailTv.getText().toString().trim();
        String password = passwordTv.getText().toString().trim();

        if (!isSchoolEmail(email))
            Toast.makeText(this, "올바른 이메일 형식이 아닙니다!", Toast.LENGTH_SHORT).show();

        Request request = new Request(email, encryption(password));

        sendPost(request);
    }

    @OnClick(R.id.link_signup)
    public void linkSignupClicked(View view) {

        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }

    public void sendPost(Request request) {

        signInService.signIn(request).enqueue(new Callback<Response>() {

            @Override
            public void onResponse(@NonNull Call<Response> call,
                                   @NonNull retrofit2.Response<Response> response) {

                if (response.isSuccessful()) {

                    if (Objects.requireNonNull(response.body()).getStatus() == 200) {

                        DatabaseHelper databaseHelper = new DatabaseHelper(
                                SignInActivity.this);

                        databaseHelper.insertToken(Objects.requireNonNull(response.body()).
                                getData().getToken());

                        Toast.makeText(SignInActivity.this,
                                Objects.requireNonNull(response.body()).getMessage(),
                                Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(SignInActivity.this,
                                MainActivity.class));

                        saveLogin();

                        finish();
                    } else {

                        Toast.makeText(SignInActivity.this,
                                Objects.requireNonNull(response.body()).getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {

                Toast.makeText(SignInActivity.this, "서버에서 응답을 받지 못했습니다",
                        Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void saveLogin() {

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id", emailTv.getText().toString());
        editor.putString("pw", passwordTv.getText().toString());
        editor.putBoolean("autoLogin", autoLoginCheckbox.isChecked());
        editor.apply();
    }
}
