package uz.brb.loan_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoanRequest {
    @NotBlank(message = "loanName can not be null or empty")
    private String loanName;
    private Double loanAmount;
    @NotBlank(message = "status can not be null or empty")
    private String status;
    @NotBlank(message = "authUserId can not be null or empty")
    private Long authUserId;
}