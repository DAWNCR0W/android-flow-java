package com.donghyeokseo.flow.network.response.notice.list;

/**
 * @author dawncrow
 */
public final class NoticeFile {
    private Long idx;
    private String uploadName;
    private String uploadDir;
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