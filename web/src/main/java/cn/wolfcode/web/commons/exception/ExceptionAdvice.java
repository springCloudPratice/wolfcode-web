package cn.wolfcode.web.commons.exception;

import cn.afterturn.easypoi.exception.excel.ExcelImportException;
import cn.hutool.json.JSONUtil;
import cn.wolfcode.web.commons.entity.CodeMsg;
import link.ahsj.core.entitys.ApiModel;
import link.ahsj.core.exception.AppServerException;
import link.ahsj.core.exception.ErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/2 9:41
 * @email chenshiyun2011@163.com
 */
@RestControllerAdvice
@Log4j2
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiModel> exceptionAdvice(Exception e) {
        log.error(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiModel.error());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiModel> httpRequestMethodNotSupportedExceptionAdvice(HttpRequestMethodNotSupportedException e) {
        log.error(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiModel.error("请求方式不正确"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiModel> methodArgumentNotValidExceptionAdvice(MethodArgumentNotValidException e) {
        log.error(e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        List<String> messages = new ArrayList<>(errors.size());
        if (!CollectionUtils.isEmpty(errors)) {
            errors.forEach(item -> messages.add(item.getDefaultMessage()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiModel.error(ErrorCode.UNVALID, CodeMsg.VALID_ERR_MSG, messages));
    }

    /**
     * 处理自定义异常
     * @param e  自定义异常
     * @return
     */
    @ExceptionHandler(AppServerException.class)
    public ResponseEntity<ApiModel> sysExceptionAdvice(AppServerException e) {
        ApiModel apiModel = JSONUtil.toBean(e.getMessage(), ApiModel.class);
        log.error(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Objects.isNull(apiModel)? ApiModel.error(e.getMessage()):apiModel);
    }

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<ApiModel> loginFailExceptionAdvice(LoginFailException e) {
        log.error(e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiModel.error(e.getMessage()));
    }

    @ExceptionHandler(ExcelImportException.class)
    public ResponseEntity<ApiModel> excelImportExceptionAdvice(ExcelImportException e) {
        log.error(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiModel.error(e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiModel> accessDeniedExceptionAdvice(AccessDeniedException e) {
        log.error(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiModel.error(e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiModel> authenticationExceptionAdvice(AuthenticationException e) {
        log.error(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiModel.error(e.getMessage()));
    }
}
