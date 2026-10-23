package nikhilsable.employee.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DepartmentServiceUnavailableException.class)
    public ResponseEntity<Map<String, String>> handleDepartmentServiceUnavailable(
            DepartmentServiceUnavailableException exception) {

        Map<String, String> response = Map.of(
                "error", "Department Service Unavailable",
                "message", exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }
}
