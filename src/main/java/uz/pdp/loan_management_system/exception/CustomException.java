package uz.pdp.loan_management_system.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class CustomException extends RuntimeException {

    private final int code;
    private final String message;

    public CustomException(HttpStatusCode httpStatusCode, String message) {
        super(String.format("%s: %s", httpStatusCode, message));
        this.code = httpStatusCode.value();
        this.message = message;
    }
}