package com.donghyeokseo.flow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.database.DatabaseHelper;
import com.donghyeokseo.flow.network.RetrofitApi;
import com.donghyeokseo.flow.network.interfaces.SignInService;
import com.donghyeokseo.flow.network.request.signin.Request;
import com.donghyeokseo.flow.network.response.signin.Response;

import java.util.Objects;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class SignInActivity extends AppCompatActivity {
    SignInService signInService;
    @BindView(R.id.login_email_editText)
    EditText emailTv;
    @BindView(R.id.login_password_editText)
    EditText passwordTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        signInService = new RetrofitApi().getSignInService();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_submit_button)
    public void OnLoginSubmitBtnClicked(View view) {
        String email = emailTv.getText().toString().trim();
        String password = passwordTv.toString().trim();
        if (!isSchoolEmail(email)) {
            Toast.makeText(this, "올바른 이메일 형식이 아닙니다!", Toast.LENGTH_SHORT).show();
        }
        Request request = new Request(email, password);
        sendPost(request);
    }

    public boolean isSchoolEmail(String email) {
        return email != null && Pattern.matches("[\\w\\~\\-\\.]+@(dgsw\\.hs\\.kr)+$", email);
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
                        databaseHelper.insertToken(Objects.requireNonNull(response.body()).getData().getToken());
                        startActivity(new Intent(SignInActivity.this,
                                MainActivity.class));
                    } else {
                        Toast.makeText(SignInActivity.this, Objects.requireNonNull(response.body()).getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                Toast.makeText(SignInActivity.this, "서버에서 응답을 받지 못했습니다",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
