package com.chenbitao.noodle_shop.vo;

import java.time.LocalDateTime;

public class ApiResponseBody<T> {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private T data;

    public ApiResponseBody() {}

    public ApiResponseBody(int status, String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = null;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponseBody<T> success(T data) {
        return new ApiResponseBody<>(200, "OK", data);
    }

    public static <T> ApiResponseBody<T> fail(int status, String error, String message, T data) {
        ApiResponseBody<T> response = new ApiResponseBody<>(status, message, data);
        response.setError(error);
        return response;
    }

    // Getter / Setter
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

}
