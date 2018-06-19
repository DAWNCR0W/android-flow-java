package com.donghyeokseo.flow.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author dawncrow
 */
public class NoticeFile {
    private Long idx;
    @SerializedName("upload_name")
    private String uploadName;
    @SerializedName("upload_dir")
    private String uploadDir;
    @SerializedName("notice_idx")
    private Long noticeIdx;

    public Long getIdx() {
        return idx;
    }

    public void setIdx(Long value) {
        this.idx = value;
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String value) {
        this.uploadName = value;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String value) {
        this.uploadDir = value;
    }

    public Long getNoticeIdx() {
        return noticeIdx;
    }

    public void setNoticeIdx(Long value) {
        this.noticeIdx = value;
    }
}