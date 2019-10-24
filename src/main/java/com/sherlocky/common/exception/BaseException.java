package com.sherlocky.common.exception;

/**
 * 公用的Exception
 * <p>
 * 继承自RuntimeException
 * </p>
 * @since 0.0.7
 */
public class BaseException extends RuntimeException {
    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
