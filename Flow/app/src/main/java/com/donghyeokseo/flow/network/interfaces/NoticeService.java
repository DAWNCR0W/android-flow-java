package com.donghyeokseo.flow.network.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface NoticeService {
    @GET("/notice")
    Call<com.donghyeokseo.flow.network.response.notice.list.Response> getNotices();

    @GET("/notice/{idx}")
    Call<com.donghyeokseo.flow.network.response.notice.detail.Response> detailNotice(
            @Path("idx") int idx
    );

    @GET
    Call<ResponseBody> downloadNoticeFile(
            @Url String url
    );
}
