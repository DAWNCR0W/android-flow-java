package com.donghyeokseo.flow.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.adapter.NoticeRecyclerAdapter;
import com.donghyeokseo.flow.model.Notice;
import com.donghyeokseo.flow.network.RetrofitApi;
import com.donghyeokseo.flow.network.interfaces.NoticeService;
import com.donghyeokseo.flow.network.response.notice.list.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author dawncrow
 */
public final class NoticeActivity extends AppCompatActivity {

    NoticeService noticeService;

    @BindView(R.id.notice_recyclerview)
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mRecyclerViewAdapter;
    RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    ArrayList<Notice> mNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        noticeService = new RetrofitApi(NoticeActivity.this).getNoticeService();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerViewLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);

        getNotices();
    }

    public void getNotices() {
        noticeService.getNotices().enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                mNotice = new ArrayList<>(Arrays.asList(response.body().getData().getList()));
                if (mNotice.get(0).getIdx() > mNotice.get(1).getIdx()) {
                    Collections.reverse(mNotice);
                }
                mRecyclerViewAdapter = new NoticeRecyclerAdapter(mNotice, NoticeActivity.this);
                mRecyclerView.setAdapter(mRecyclerViewAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                Toast.makeText(NoticeActivity.this, "서버에서 응답을 받지 못했습니다",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
