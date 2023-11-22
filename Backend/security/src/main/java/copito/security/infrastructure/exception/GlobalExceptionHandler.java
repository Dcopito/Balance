package copito.security.infrastructure.exception;

import copito.security.domain.exceptions.AccountEntityAlreadyExists;
import copito.security.domain.model.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionDTO> handlerExceptions(Exception e){
        var exceptionDto = ExceptionDTO.builder()
                .detail(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .type(e.getClass().getTypeName())
                .build();

        return ResponseEntity.badRequest().body(exceptionDto);
    }

    @ExceptionHandler(value = AccountEntityAlreadyExists.class)
    public ResponseEntity<ExceptionDTO> handlerConflictException(AccountEntityAlreadyExists e){
        var exceptionDto = ExceptionDTO.builder()
                .detail(e.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .type(e.getClass().getTypeName())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionDto);
    }
}
