package com.donghyeokseo.flow.network.response.notice.detail;

/**
 * @author dawncrow
 */
public class Data {
    private Long idx;
    private String content;
    private String writer;
    private String writeDate;
    private String modifyDate;
    private NoticeFile[] noticeFiles;

    public Long getIdx() {
        return idx;
    }

    public void setIdx(Long value) {
        this.idx = value;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String value) {
        this.content = value;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String value) {
        this.writer = value;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String value) {
        this.writeDate = value;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String value) {
        this.modifyDate = value;
    }

    public NoticeFile[] getNoticeFiles() {
        return noticeFiles;
    }

    public void setNoticeFiles(NoticeFile[] value) {
        this.noticeFiles = value;
    }
}