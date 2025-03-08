package uz.pdp.loan_management_system.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.loan_management_system.dto.ErrorDTO;
import uz.pdp.loan_management_system.dto.ResponseDTO;

import java.util.List;

@RestController
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

    @ExceptionHandler(CustomUserNotFoundException.class)
    public ResponseDTO<Void> handleUserNotFoundException(CustomUserNotFoundException customUserNotFoundException) {
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.NOT_FOUND.value()) // user not found
                .message(customUserNotFoundException.getMessage())
                .success(false)
                .build();
    }
}
