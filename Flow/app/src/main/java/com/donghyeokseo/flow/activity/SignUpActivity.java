package com.donghyeokseo.flow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.network.RetrofitApi;
import com.donghyeokseo.flow.network.interfaces.SignService;
import com.donghyeokseo.flow.network.request.signup.Request;
import com.donghyeokseo.flow.network.response.signup.Response;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

import static com.donghyeokseo.flow.Util.encryption;
import static com.donghyeokseo.flow.Util.hasSpecialCharacter;
import static com.donghyeokseo.flow.Util.isSchoolEmail;
import static com.donghyeokseo.flow.Util.isValidPassword;

public final class SignUpActivity extends AppCompatActivity {

    private int classIdx = 0;
    private int classNumber = 0;

    SignService signService = new RetrofitApi(SignUpActivity.this).getSignService();

    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.input_password_repeat)
    EditText inputPasswordRepeat;
    @BindView(R.id.input_phone)
    EditText inputPhone;
    @BindView(R.id.input_spinner_class_idx)
    Spinner inputSpinnerClassIdx;
    @BindView(R.id.input_spinner_class_number)
    Spinner inputSpinnerClassNumber;
    @BindView(R.id.input_radio_group)
    RadioGroup radioGroup;

    @OnClick(R.id.link_login)
    public void loginLinkClicked() {

        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
        finish();
    }

    @OnClick(R.id.button_signup)
    public void btnSignupClicked(View view) {

        int radioBtnSelectedId = radioGroup.getCheckedRadioButtonId();

        String gender = ((RadioButton) findViewById(radioBtnSelectedId)).getText().toString();

        if (checkInputHasValue()) return;

        //이메일 정규식 검사
        if (isSchoolEmail(inputEmail.getText().toString().trim()))
            //비밀번호 정규식 검사
            if (isValidPassword(inputPassword.getText().toString().trim()))
                //비밀번호 특수문자 포함 검사
                if (hasSpecialCharacter(inputPassword.getText().toString().trim()))
                    //비밀번호 재입력 검사
                    if (inputPassword.getText().toString().equals(
                            inputPasswordRepeat.getText().toString().trim())) {

                        //요청파라미터 객체 생성 및 초기화
                        Request request = new Request(
                                inputEmail.getText().toString().trim(),
                                encryption(inputPassword.getText().toString().trim()),
                                inputName.getText().toString().trim(),
                                gender,
                                inputPhone.getText().toString().trim(),
                                classIdx,
                                classNumber
                        );

                        sendPost(request);
                    } else
                        Toast.makeText(this,
                                R.string.wrong_password, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,
                            R.string.require_special_char, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, R.string.invalid_password, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, R.string.invalid_email, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        radioGroup.check(R.id.input_radio_button_male);
        ArrayAdapter<CharSequence> classIdx = ArrayAdapter.createFromResource(this,
                R.array.class_idx, android.R.layout.simple_spinner_item);

        classIdx.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        inputSpinnerClassIdx.setAdapter(classIdx);

        ArrayAdapter<CharSequence> classNumber = ArrayAdapter.createFromResource(this,
                R.array.class_number, android.R.layout.simple_spinner_item);

        classNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        inputSpinnerClassNumber.setAdapter(classNumber);

        inputSpinnerClassIdx.setOnItemSelectedListener(new ClassIdxItemSelectListener());
        inputSpinnerClassNumber.setOnItemSelectedListener(new ClassNumberItemSelectListener());
    }

    private boolean checkInputHasValue() {
        if (classIdx == 0 ||
                classNumber == 0 ||
                inputName.getText().toString().trim().equals("") ||
                inputEmail.getText().toString().trim().equals("") ||
                inputPassword.getText().toString().trim().equals("") ||
                inputPhone.getText().toString().trim().equals("")) {

            Toast.makeText(this, "빈 칸을 채워 주세요", Toast.LENGTH_SHORT).show();

            return true;
        }
        return false;
    }

    private void sendPost(Request request) {

        Log.e("signup", request.getPw());

        signService.signUp(request).enqueue(new Callback<Response>() {

            @Override
            public void onResponse(@NonNull Call<Response> call,
                                   @NonNull retrofit2.Response<Response> response) {

                if (Objects.requireNonNull(response.body()).getStatus() == 200) {

                    Intent i = new Intent(SignUpActivity.this,
                            MainActivity.class);

                    Toast.makeText(SignUpActivity.this,
                            Objects.requireNonNull(response.body()).getMessage(),
                            Toast.LENGTH_SHORT).show();

                    startActivity(i);

                    finish();
                } else {

                    Toast.makeText(SignUpActivity.this,
                            Objects.requireNonNull(response.body()).getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response> call,
                                  @NonNull Throwable t) {

                Toast.makeText(SignUpActivity.this,
                        "서버에서 응답을 받지 못했습니다",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class ClassIdxItemSelectListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            classIdx = Integer.parseInt(parent.getItemAtPosition(position).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class ClassNumberItemSelectListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            classNumber = Integer.parseInt(parent.getItemAtPosition(position).toString());

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
