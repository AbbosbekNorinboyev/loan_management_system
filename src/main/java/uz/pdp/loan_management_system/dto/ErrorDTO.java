package uz.pdp.loan_management_system.dto;

import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErrorDTO {
    private String field;
    private String message;
}
