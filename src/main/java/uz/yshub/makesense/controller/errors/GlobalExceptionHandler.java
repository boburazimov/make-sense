package uz.yshub.makesense.controller.errors;

import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler implements ProblemHandling, SecurityAdviceTrait {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(BadRequestAlertException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrRespModel> handleBadRequestAlertException(BadRequestAlertException exception) {
        log.error(exception.getMessage(), exception);

        return ResponseEntity.badRequest()
                .body(ErrRespModel.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .cause(exception.getMessage())
                        .entityName(exception.getEntityName())
                        .errorKey(exception.getErrorKey())
                        .timestamp(LocalDateTime.now())
                        .build());
    }


    @ExceptionHandler(JDBCConnectionException.class)
    public ResponseEntity<ErrRespModel> handleConnectionError(Exception ex) {
        log.error(ex.getMessage(), ex);

        return ResponseEntity.badRequest()
                .body(ErrRespModel.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .cause(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
