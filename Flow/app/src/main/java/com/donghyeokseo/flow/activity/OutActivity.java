package com.donghyeokseo.flow.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.network.RetrofitApi;
import com.donghyeokseo.flow.network.interfaces.OutService;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class OutActivity extends AppCompatActivity {

    private OutService outService;

    private Timestamp currentTime = new Timestamp(System.currentTimeMillis());

    private boolean isSleep = false;

    final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    @BindView(R.id.textview_reason_out)
    TextView reasonOutTv;
    @BindView(R.id.textview_start_out_time)
    TextView startOutTimeTv;
    @BindView(R.id.textview_end_out_time)
    TextView endOutTimeTv;

    @BindView(R.id.edittext_reason_out)
    EditText reasonOutEt;
    @BindView(R.id.edittext_start_out_time)
    EditText startOutTimeEt;
    @BindView(R.id.edittext_end_out_time)
    EditText endOutTimeEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_out);
        ButterKnife.bind(this);
        isSleep = getIntent().getBooleanExtra("IsSleep", false);
        if (isSleep)
            setOutSleep();
        else
            setOutGo();
        outService = new RetrofitApi(OutActivity.this).getOutService();
    }

    private void setOutGo() {
        reasonOutTv.setText("외출 사유");
        startOutTimeTv.setText("외출 시작 시간");
        endOutTimeTv.setText("외출 복귀 시간");
    }

    private void setOutSleep() {
        reasonOutTv.setText("외박 사유");
        startOutTimeTv.setText("외박 시작 시간");
        endOutTimeTv.setText("외박 복귀 시간");
    }

    @OnClick(R.id.button_out_submit)
    public void onOutSubmitBtnClicked(View view) {

        String reason = reasonOutEt.getText().toString().trim();
        Timestamp startTime;
        Timestamp endTime;
        try {
            startTime = new java.sql.Timestamp(new SimpleDateFormat(DATE_FORMAT, Locale.KOREA).
                    parse(startOutTimeEt.getText().toString()).getTime());
            endTime = new java.sql.Timestamp(new SimpleDateFormat(DATE_FORMAT, Locale.KOREA).
                    parse(endOutTimeEt.getText().toString()).getTime());
        } catch (ParseException ignored) {
            Toast.makeText(this, "정상적인 날짜를 선택해 주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkError(reason, startTime, endTime)) return;

        if (isSleep) {
            com.donghyeokseo.flow.network.request.outsleep.Request request =
                    new com.donghyeokseo.flow.network.request.outsleep.Request();
            request.setEndTime(endTime);
            request.setStartTime(startTime);
            request.setReason(reason);

            sendOutSleepPost(request);
        } else {
            com.donghyeokseo.flow.network.request.outgo.Request request =
                    new com.donghyeokseo.flow.network.request.outgo.Request();
            request.setEndTime(endTime);
            request.setReason(reason);
            request.setStartTime(startTime);

            sendOutGoPost(request);
        }
    }

    private boolean checkError(String reason, Timestamp startTime, Timestamp endTime) {
        if (reason.equals("")) {
            Toast.makeText(this, "사유를 입력해 주세요", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (currentTime.before(startTime)) {
            Toast.makeText(this, "올바른 시작 날짜를 입력해 주세요", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (currentTime.before(endTime)) {
            Toast.makeText(this, "올바른 복귀 날짜를 입력해 주세요", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (startTime.after(endTime)) {
            Toast.makeText(this, "시작 날짜와 복귀 날짜를 확인해 주세요", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void sendOutGoPost(com.donghyeokseo.flow.network.request.outgo.Request request) {
        outService.outGo(request).enqueue(
                new Callback<com.donghyeokseo.flow.network.response.outgo.Response>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<com.donghyeokseo.flow.network.response.outgo.Response> call,
                            @NonNull retrofit2.Response<com.donghyeokseo.flow.network.response.outgo.Response> response
                    ) {

                        Toast.makeText(OutActivity.this,
                                Objects.requireNonNull(response.body()).getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<com.donghyeokseo.flow.network.response.outgo.Response> call,
                            @NonNull Throwable t) {

                        Toast.makeText(OutActivity.this,
                                "서버에서 응답을 받지 못했습니다",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendOutSleepPost(com.donghyeokseo.flow.network.request.outsleep.Request request) {
        outService.outSleep(request).enqueue(
                new Callback<com.donghyeokseo.flow.network.response.outsleep.Response>() {
                    @Override
                    public void onResponse(
                            @NonNull Call<com.donghyeokseo.flow.network.response.outsleep.Response> call,
                            @NonNull retrofit2.Response<com.donghyeokseo.flow.network.response.outsleep.Response> response
                    ) {

                        Toast.makeText(OutActivity.this,
                                Objects.requireNonNull(response.body()).getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<com.donghyeokseo.flow.network.response.outsleep.Response> call,
                            @NonNull Throwable t) {

                        Toast.makeText(OutActivity.this,
                                "서버에서 응답을 받지 못했습니다",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @OnClick(R.id.edittext_start_out_time)
    public void startOutTimeClicked(View view) {

    }

    @OnClick(R.id.edittext_end_out_time)
    public void endOutTimeClicked(View view) {

    }
}
