package com.ssapick.server.core.response;

public class ErrorResponse extends BaseResponse<Void> {
    private final static ErrorResponse EMPTY = new ErrorResponse();

    private ErrorResponse() {
        this.success = false;
        this.status = 500;
        this.message = "서버 에러입니다. 관리자에게 문의해주세요.";
    }

    public static ErrorResponse empty() {
        return EMPTY;
    }
}
