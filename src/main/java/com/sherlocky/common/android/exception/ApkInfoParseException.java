package com.sherlocky.common.android.exception;

/**
 * apk信息解析异常
 */
public class ApkInfoParseException extends RuntimeException {

    private static final long serialVersionUID = -8067284559734560285L;

    public ApkInfoParseException(String message) {
        super(message);
    }

    public ApkInfoParseException(String message, Throwable ex) {
        super(message, ex);
    }
}
