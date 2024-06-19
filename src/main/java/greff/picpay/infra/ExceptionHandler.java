package greff.picpay.infra;

import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<JSONException> handleDatabaseException(SQLException e){
        return ResponseEntity.badRequest().body(new JSONException("database error",e.getMessage()));
    }
}
