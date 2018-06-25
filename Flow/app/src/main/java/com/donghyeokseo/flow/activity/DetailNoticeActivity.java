package com.donghyeokseo.flow.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.adapter.DetailNoticeDownloadRecyclerAdapter;
import com.donghyeokseo.flow.model.NoticeFile;
import com.donghyeokseo.flow.network.RetrofitApi;
import com.donghyeokseo.flow.network.interfaces.NoticeService;
import com.donghyeokseo.flow.network.response.notice.detail.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author dawncrow
 */
public final class DetailNoticeActivity extends AppCompatActivity {

    private NoticeService noticeService;
    private NoticeFile[] noticeFiles;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;

    @BindView(R.id.detail_notice_idx_textview)
    TextView idx;
    @BindView(R.id.detail_notice_author_textview)
    TextView author;
    @BindView(R.id.detail_notice_content_textview)
    TextView content;
    @BindView(R.id.detail_notice_date_textview)
    TextView date;
    @BindView(R.id.detail_notice_download_root_view)
    RecyclerView downloadRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notice);
        ButterKnife.bind(this);

        noticeService = new RetrofitApi(DetailNoticeActivity.this).getNoticeService();

        downloadRootView.setHasFixedSize(true);
        mRecyclerViewLayoutManager = new LinearLayoutManager(this);
        downloadRootView.setLayoutManager(mRecyclerViewLayoutManager);

        getNotice();
    }

    public void getNotice() {
        noticeService
                .detailNotice(getIntent().getIntExtra("idx", 0))
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()) {

                            if (Objects.requireNonNull(response.body()).getStatus() == 200) {
                                //텍스트뷰 초기화
                                idx.setText(String.valueOf(response.body().getData().getIdx()));
                                author.setText(response.body().getData().getWriter());
                                content.setText(response.body().getData().getContent());
                                date.setText(response.body().getData().getModifyDate());

                                mRecyclerViewAdapter
                                        = new DetailNoticeDownloadRecyclerAdapter(
                                        new ArrayList<>(
                                                Arrays.asList(response.body().getData().getNoticeFiles())),
                                        DetailNoticeActivity.this
                                        , noticeService);

                                downloadRootView.setAdapter(mRecyclerViewAdapter);

                                DividerItemDecoration dividerItemDecoration =
                                        new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(DetailNoticeActivity.this).getOrientation());
                                downloadRootView.addItemDecoration(dividerItemDecoration);
                            } else {
                                Toast.makeText(DetailNoticeActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Toast.makeText(DetailNoticeActivity.this, "서버에서 응답을 받지 못했습니다",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
