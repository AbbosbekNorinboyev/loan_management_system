package uz.pdp.loan_management_system.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.loan_management_system.dto.*;

import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandle {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDTO<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorDTO> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> {
                    String field = fieldError.getField();
                    String defaultMessage = fieldError.getDefaultMessage();
                    String rejectedValue = String.valueOf(fieldError.getRejectedValue());
                    return new ErrorDTO(
                            field,
                            String.format("defaultMessage: '%s', rejectionValue: '%s'", defaultMessage, rejectedValue)
                    );
                }).toList();
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.BAD_REQUEST.value())  // Bad request kodi
                .message("Validation error")
                .success(false)
                .errors(errors)
                .build();
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex) {
        // Handle the InvalidFormatException here
        String errorMessage = "Invalid format for field " + ex.getPath().get(0).getFieldName();
        var error = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(errorMessage)
                .build();

        var responseData = Response.builder()
                .success(false)
                .error(error)
                .data(Empty.builder().build())
                .build();
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseDTO<Void> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.NOT_FOUND.value()) // not found
                .message(resourceNotFoundException.getMessage())
                .success(false)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseDTO<Void> handleException(Exception exception) {
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value()) // Internal Server Error
                .message("Something wrong -> " + exception.getMessage())
                .success(false)
                .build();
    }
}
