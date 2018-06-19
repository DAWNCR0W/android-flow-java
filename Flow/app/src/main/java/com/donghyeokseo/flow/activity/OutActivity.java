package com.donghyeokseo.flow.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.Util;
import com.donghyeokseo.flow.database.DatabaseHelper;
import com.donghyeokseo.flow.model.Out;
import com.donghyeokseo.flow.network.RetrofitApi;
import com.donghyeokseo.flow.network.interfaces.OutService;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author dawncrow
 */
public final class OutActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private boolean isStart;
    private boolean isSleep = false;
    private OutService outService;
    private Out out = new Out();
    private Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    private DatabaseHelper databaseHelper;

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

        databaseHelper = new DatabaseHelper(this);

        isSleep = getIntent().getBooleanExtra("IsSleep", false);

        if (isSleep) {
            setOutSleep();
        } else {
            setOutGo();
        }

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

    private void showDatePicker() {

        if (isStart) {
            startOutTimeEt.setText("");
        } else {
            endOutTimeEt.setText("");
        }

        final Calendar c = Calendar.getInstance();

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog =
                new DatePickerDialog(OutActivity.this, this, mYear, mMonth, mDay);

        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }

    @OnClick(R.id.button_out_submit)
    public void onOutSubmitBtnClicked(View view) {

        String reason = reasonOutEt.getText().toString().trim();
        Timestamp startTime;
        Timestamp endTime;
        String startTimeStr;
        String endTimeStr;
        String DATE_FORMAT = "yyyy-MM-dd HH:mm";

        try {

            startTime = new java.sql.Timestamp(new SimpleDateFormat(DATE_FORMAT, Locale.KOREA).
                    parse(startOutTimeEt.getText().toString()).getTime());

            endTime = new java.sql.Timestamp(new SimpleDateFormat(DATE_FORMAT, Locale.KOREA).
                    parse(endOutTimeEt.getText().toString()).getTime());

            startTimeStr = startOutTimeEt.getText().toString().trim();

            endTimeStr = endOutTimeEt.getText().toString().trim();
        } catch (ParseException ignored) {

            Toast.makeText(this, "올바른 날짜를 선택해 주세요", Toast.LENGTH_SHORT).show();

            return;
        }

        if (checkError(reason, startTime, endTime)) {
            return;
        }

        if (isSleep) {

            com.donghyeokseo.flow.network.request.outsleep.Request request =
                    new com.donghyeokseo.flow.network.request.outsleep.Request();

            try {

                request.setEndTime(new SimpleDateFormat(DATE_FORMAT, Locale.KOREA)
                        .format(new SimpleDateFormat(DATE_FORMAT, Locale.KOREA).parse(endTimeStr)));

                request.setStartTime(new SimpleDateFormat(DATE_FORMAT, Locale.KOREA)
                        .format(new SimpleDateFormat(DATE_FORMAT, Locale.KOREA).parse(startTimeStr)));

                request.setReason(reason);

                out.setStatusCode(Util.OUTSLEEP_REQUESTED);
                out.setReason(request.getReason());
                out.setEndTime(request.getEndTime());
                out.setStartTime(request.getStartTime());
            } catch (Exception ignored) {
            }

            sendOutSleepPost(request);
        } else {

            com.donghyeokseo.flow.network.request.outgo.Request request =
                    new com.donghyeokseo.flow.network.request.outgo.Request();

            try {
                request.setEndTime(new SimpleDateFormat(DATE_FORMAT, Locale.KOREA)
                        .format(new SimpleDateFormat(DATE_FORMAT, Locale.KOREA).parse(endTimeStr)));

                request.setStartTime(new SimpleDateFormat(DATE_FORMAT, Locale.KOREA)
                        .format(new SimpleDateFormat(DATE_FORMAT, Locale.KOREA).parse(startTimeStr)));

                request.setReason(reason);

                out.setStatusCode(Util.OUTGO_REQUESTED);
                out.setReason(request.getReason());
                out.setEndTime(request.getEndTime());
                out.setStartTime(request.getStartTime());
            } catch (Exception ignored) {
            }

            sendOutGoPost(request);
        }
    }

    private boolean checkError(String reason, Timestamp startTime, Timestamp endTime) {
        if (reason.equals("")) {

            Toast.makeText(this, "사유를 입력해 주세요", Toast.LENGTH_SHORT).show();

            return true;
        }

        if (!currentTime.before(startTime)) {

            Toast.makeText(this, "올바른 시작 날짜를 입력해 주세요", Toast.LENGTH_SHORT).show();

            return true;
        }

        if (!currentTime.before(endTime)) {

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

                        if (Objects.requireNonNull(response.body()).getStatus() == 200) {

                            databaseHelper.insertOut(out);

                            startActivity(new Intent(OutActivity.this, MainActivity.class));

                            finish();
                        }
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

                        if (Objects.requireNonNull(response.body()).getStatus() == 200) {

                            databaseHelper.insertOut(out);

                            startActivity(new Intent(OutActivity.this, MainActivity.class));

                            finish();
                        }
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

        isStart = true;

        showDatePicker();
    }

    @OnClick(R.id.edittext_end_out_time)
    public void endOutTimeClicked(View view) {

        isStart = false;

        showDatePicker();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if (isStart) {
            startOutTimeEt.setText(
                    year
                            + "-"
                            + String.format(Locale.KOREA, "%02d", (month + 1))
                            + "-"
                            + String.format(Locale.KOREA, "%02d", dayOfMonth));
        } else {
            endOutTimeEt.setText(
                    year
                            + "-"
                            + String.format(Locale.KOREA, "%02d", (month + 1))
                            + "-"
                            + String.format(Locale.KOREA, "%02d", dayOfMonth));
        }

        Calendar mCurrentTime = Calendar.getInstance();

        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(OutActivity.this, this, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.setCancelable(false);
        mTimePicker.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (isStart) {
            startOutTimeEt.setText(
                    startOutTimeEt.getText().toString()
                            + " "
                            + String.format(Locale.KOREA, "%02d", hourOfDay)
                            + ":"
                            + String.format(Locale.KOREA, "%02d", minute)
                            + ":00");
        } else {
            endOutTimeEt.setText(
                    endOutTimeEt.getText().toString()
                            + " "
                            + String.format(Locale.KOREA, "%02d", hourOfDay)
                            + ":"
                            + String.format(Locale.KOREA, "%02d", minute)
                            + ":00");
        }
    }
}
