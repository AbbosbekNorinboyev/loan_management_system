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
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream().map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage()).toList();
        StringBuilder stringBuilder = new StringBuilder();
        errors.forEach(s -> stringBuilder.append(s).append(System.lineSeparator()));
        String errorMessage = !stringBuilder.toString().isEmpty() ? stringBuilder.toString() : e.getMessage();

        ErrorResponse validationError = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())  // Bad request kodi
                .message(errorMessage)
                .build();

        var response = Response.builder()
                .success(false)
                .error(validationError)
                .data(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
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

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleGeneralCustomExceptions(CustomException customException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(customException.getCode())
                .message(customException.getMessage())
                .build();

        var response = Response.builder()
                .success(false)
                .error(errorResponse)
                .data(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public Response resourceNotFoundException(ResourceNotFoundException e) {
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
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
