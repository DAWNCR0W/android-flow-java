package com.donghyeokseo.flow.network.response.signup;

/**
 * @author dawncrow
 */
public final class Response {
    private Long status;
    private String message;

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
}
