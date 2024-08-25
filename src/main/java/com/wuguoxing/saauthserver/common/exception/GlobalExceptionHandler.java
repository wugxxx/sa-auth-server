package com.wuguoxing.saauthserver.common.exception;

import cn.dev33.satoken.exception.NotPermissionException;
import com.wuguoxing.saauthserver.dto.common.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(Exception e) {
        log.error("服务器异常：", e);
        return ResponseEntity.fail(500, "服务器异常");
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> handleRuntimeException(RuntimeException e) {
        log.error("运行时异常", e);
        return ResponseEntity.fail(400, e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Void> handleBusinessException(BusinessException e) {
        log.warn("业务异常：{}", e.getMessage());
        return ResponseEntity.fail(400, e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Void> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.warn("无资源异常：{}", e.getMessage());
        return ResponseEntity.fail(404, e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Void> handleForbiddenException(ForbiddenException e) {
        log.warn("业务禁止异常：{}", e.getMessage());
        return ResponseEntity.fail(403, e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(NotPermissionException.class)
    public ResponseEntity<Void> handleNotPermissionException(NotPermissionException e) {
        log.warn("无权限异常：{}", e.getMessage());
        return ResponseEntity.fail(403, "无此权限");
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Map<String, Object>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("参数校验异常：{}", e.getMessage());
        List<Map<String, Object>> list = new ArrayList<>();
        for (ObjectError objectError : e.getBindingResult().getAllErrors()) {
            Map<String, Object> map = new HashMap<>();
            if (objectError instanceof FieldError fieldError) {
                map.put("field", fieldError.getField());
                map.put("message", fieldError.getDefaultMessage());
            } else {
                map.put("field", objectError.getObjectName());
                map.put("message", objectError.getDefaultMessage());
            }
            list.add(map);
        }
        return ResponseEntity.fail(400, "参数错误", list);
    }
}
