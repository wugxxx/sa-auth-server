package com.wuguoxing.saauthserver.dto.common;

import lombok.Getter;
import lombok.Setter;

/**
 * 统一返回对象
 */
@Getter
@Setter
public class ResponseEntity<T> {

    private int code;
    private String message;
    private T data;

    public ResponseEntity() {
    }

    public ResponseEntity(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseEntity(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 静态方法，方便快速创建 Response 对象
    public static <T> ResponseEntity<T> ok(T data) {
        return new ResponseEntity<>(200, "Success", data);
    }

    public static <T> ResponseEntity<T> ok() {
        return new ResponseEntity<>(200, "Success");
    }

    public static <T> ResponseEntity<T> fail(int code, String message) {
        return new ResponseEntity<>(code, message, null);
    }

    public static <T> ResponseEntity<T> fail(int code, String message, T data) {
        return new ResponseEntity<>(code, message, data);
    }
}

