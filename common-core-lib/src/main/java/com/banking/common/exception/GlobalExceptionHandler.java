package com.banking.common.exception;


import com.banking.common.context.RequestContext;
import com.banking.common.context.RequestContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(CoreException.class)
    public ResponseEntity<ErrorResponse> handleCoreException(CoreException ex, HttpServletRequest request, Locale locale){
       return buildErrorResponse(ex.getErrorCode(), request, locale, ex, false);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,HttpServletRequest request,Locale locale ){
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String msg = (fieldError != null) ? fieldError.getDefaultMessage() : "validation error";
        return buildErrorResponse(ErrorCode.VALIDATION_ERROR, request, locale, msg, ex, false);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request, Locale locale){
        return buildErrorResponse(ErrorCode.GATEWAY_FORBIDDEN, request, locale, ex, false);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpServletRequest request, Locale locale){
        return buildErrorResponse(ErrorCode.BAD_REQUEST, request, locale, ex, false);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknow(Exception ex, HttpServletRequest request, Locale locale){
        return  buildErrorResponse(ErrorCode.INTERNAL_ERROR, request, locale, ex, true);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(ErrorCode code, HttpServletRequest request, Locale locale, Exception ex, boolean isCritical){
        return buildErrorResponse(code, request, locale, null, ex, isCritical);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(ErrorCode code, HttpServletRequest request, Locale locale, String customMessage, Exception ex, boolean isCritical) {
      RequestContext ctx = RequestContextHolder.getContext();
      String message = (customMessage != null) ? customMessage : messageSource.getMessage(code.name(), null, code.name(), locale);

        ErrorResponse response = new ErrorResponse(
                code.name(),
                message,
                request.getRequestURI(),
                ctx.getCorrelationId()
        );

        if (isCritical){
            log.error("[{}] {} - {}", ctx.getCorrelationId(), code.name(), ex.getMessage());
        }else {
            log.warn("[{}] {} - {}", ctx.getCorrelationId(), code.name(), message);
        }

        return new ResponseEntity<>(response, code.httpStatus());

    }



}
