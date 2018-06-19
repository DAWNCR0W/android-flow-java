package com.donghyeokseo.flow.network.response.notice.list;

import com.donghyeokseo.flow.model.Notice;

/**
 * @author dawncrow
 */
public final class Data {
    private Notice[] list;

    public Notice[] getList() {
        return list;
    }

    public void setList(Notice[] value) {
        this.list = value;
    }
}