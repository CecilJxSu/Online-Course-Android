package cn.canlnac.onlinecourse.data.exception;

/**
 * 响应状态码异常.
 */

public class ResponseStatusException extends Exception {
    public int code;

    public ResponseStatusException(int code) {
        super();
        this.code = code;
    }

    public ResponseStatusException(int code, String detailMessage) {
        super(detailMessage);
        this.code = code;
    }

    public ResponseStatusException(int code, String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        this.code = code;
    }

    public ResponseStatusException(Throwable throwable) {
        super(throwable);
    }
}
