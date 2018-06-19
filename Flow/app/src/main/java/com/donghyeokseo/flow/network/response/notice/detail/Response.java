package com.donghyeokseo.flow.network.response.notice.detail;

import android.support.annotation.Nullable;

import com.donghyeokseo.flow.model.Notice;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * @author dawncrow
 */
public class Response extends ResponseBody {
    private Long status;
    private String message;
    private Notice data;

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long value) {
        this.status = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    public Notice getData() {
        return data;
    }

    public void setData(Notice value) {
        this.data = value;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public long contentLength() {
        return 0;
    }

    @Override
    public BufferedSource source() {
        return null;
    }
}