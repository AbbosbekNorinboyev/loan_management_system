package uz.pdp.loan_management_system.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.loan_management_system.dto.Empty;
import uz.pdp.loan_management_system.dto.ErrorResponse;
import uz.pdp.loan_management_system.dto.Response;

import java.time.LocalDateTime;
import java.util.List;

import static uz.pdp.loan_management_system.util.Util.localDateTimeFormatter;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandle {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                   HttpServletRequest request) {
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
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
                                                               HttpServletRequest request) {
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
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleGeneralCustomExceptions(CustomException customException,
                                                           HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(customException.getCode())
                .message(customException.getMessage())
                .build();

        var response = Response.builder()
                .success(false)
                .error(errorResponse)
                .data(Empty.builder().build())
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public Response<?, ?> resourceNotFoundException(ResourceNotFoundException e,
                                                    HttpServletRequest request) {
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .success(false)
                .message(e.getMessage())
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception,
                                             HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .build();

        var response = Response.builder()
                .success(false)
                .error(errorResponse)
                .data(Empty.builder().build())
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
