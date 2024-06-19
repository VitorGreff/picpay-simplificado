package greff.picpay.infra;

import greff.picpay.exceptions.InsufficientBalanceException;
import greff.picpay.exceptions.UnauthorizedTransactionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<JSONException> handleDatabaseException(SQLException e){
        return ResponseEntity.badRequest().body(new JSONException("database error",e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<JSONException> handleValidationException(InsufficientBalanceException e){
        return ResponseEntity.badRequest().body(new JSONException("insufficient balance",e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<JSONException> handleUnauthorizedException(UnauthorizedTransactionException e){
        return ResponseEntity.badRequest().body(new JSONException("unauthorized",e.getMessage()));
    }
}
