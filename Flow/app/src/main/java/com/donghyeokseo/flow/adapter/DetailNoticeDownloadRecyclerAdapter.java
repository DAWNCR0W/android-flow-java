package com.donghyeokseo.flow.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.donghyeokseo.flow.R;
import com.donghyeokseo.flow.Util;
import com.donghyeokseo.flow.model.NoticeFile;
import com.donghyeokseo.flow.network.interfaces.NoticeService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author dawncrow
 */
public final class DetailNoticeDownloadRecyclerAdapter extends RecyclerView.Adapter {

    private ArrayList<NoticeFile> mList;
    private Context context;
    private NoticeService noticeService;

    public DetailNoticeDownloadRecyclerAdapter(ArrayList<NoticeFile> mList, Context context, NoticeService noticeService) {
        this.mList = mList;
        this.context = context;
        this.noticeService = noticeService;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detail_notice_download, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    final class ListViewHolder extends RecyclerView.ViewHolder {

        String fileDir;

        @BindView(R.id.item_detail_notice_download_file_name)
        TextView fileName;

        ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(NoticeFile list) {
            fileName.setText(list.getUploadName());
            fileDir = list.getUploadDir();
        }

        @OnClick(R.id.item_detail_notice_download_button)
        public void onDownloadBtnClicked(View view) {

            //최종 파일 주소
            String url = Util.SERVER_HOST + fileDir;

            noticeService.downloadNoticeFile(url).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    boolean writted = writeFileToDisk(response.body());
                    Toast.makeText(context, "저장되었습니다", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                }
            });
        }

        /**
         * 파일을 받아서 저장하는 메소드
         *
         * @param body 서버에서 불러온 파일
         */
        private boolean writeFileToDisk(ResponseBody body) {
            try {
                // todo change the file location/name according to your needs
                File futureStudioIconFile = new File(context.getExternalFilesDir(null) + File.separator + fileName.getText().toString());

                InputStream inputStream = null;
                OutputStream outputStream = null;

                try {
                    byte[] fileReader = new byte[4096];

                    long fileSize = body.contentLength();
                    long fileSizeDownloaded = 0;

                    inputStream = body.byteStream();
                    outputStream = new FileOutputStream(futureStudioIconFile);

                    while (true) {
                        int read = inputStream.read(fileReader);

                        if (read == -1) {
                            break;
                        }

                        outputStream.write(fileReader, 0, read);

                        fileSizeDownloaded += read;

                    }

                    outputStream.flush();

                    return true;
                } catch (IOException e) {
                    return false;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }

                    if (outputStream != null) {
                        outputStream.close();
                    }
                }
            } catch (IOException e) {
                return false;
            }
        }
    }
}
