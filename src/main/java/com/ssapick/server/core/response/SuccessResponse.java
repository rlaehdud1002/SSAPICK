package com.ssapick.server.core.response;

import lombok.Getter;

@Getter
public class SuccessResponse<T> extends BaseResponse<T> {
    private final static SuccessResponse<Void> EMPTY = new SuccessResponse<>();

    private SuccessResponse(T data) {
        this.success = true;
        this.status = 200;
        this.message = "success";
        this.data = data;
    }

    private SuccessResponse() {
        this.success = true;
        this.status = 200;
        this.message = "success";
    }

    public static <T> SuccessResponse<T> of(T data) {
        return new SuccessResponse<>(data);
    }

    public static SuccessResponse<Void> empty() {
        return EMPTY;
    }
}
