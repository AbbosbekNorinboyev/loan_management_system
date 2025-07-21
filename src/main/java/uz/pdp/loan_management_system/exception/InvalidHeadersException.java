package uz.pdp.loan_management_system.exception;

public class InvalidHeadersException extends RuntimeException  {
    public InvalidHeadersException(String msg) {
        super(msg);
    }

    public InvalidHeadersException(String msg, Throwable cause) {
        super(msg, cause);
    }
}