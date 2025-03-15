package sk.tuke.fpa_tool_ws.exception;

import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sk.tuke.fpa_tool_ws.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CalculationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<String>> handleCalculationNotFound(CalculationNotFoundException ex) {
        ApiResponse<String> response = new ApiResponse<>(400, ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Exception handler for UnexpectedTypeException when the request body is not valid
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(UnexpectedTypeException ex) {
        ApiResponse<String> response = new ApiResponse<>(400, ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

