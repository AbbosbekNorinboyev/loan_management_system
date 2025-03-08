package uz.pdp.loan_management_system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {
    private int code;
    private String message;
    private boolean success;
    private T data;
    private List<ErrorDTO> errors;
}
