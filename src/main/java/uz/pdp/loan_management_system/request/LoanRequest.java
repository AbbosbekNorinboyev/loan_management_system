package uz.pdp.loan_management_system.request;

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
    private Long authUserId;
}