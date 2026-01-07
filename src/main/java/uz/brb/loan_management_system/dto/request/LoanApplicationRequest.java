package uz.brb.loan_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.brb.loan_management_system.enums.LoanApplicationStatus;

import java.math.BigDecimal;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoanApplicationRequest {
    @NotBlank(message = "amount can not be null or empty")
    private BigDecimal amount;

    private Integer tenureMonths;

    @NotBlank(message = "status can not be null or empty")
    private LoanApplicationStatus status;

    @NotBlank(message = "authUserId can not be null or empty")
    private Long authUserId;
}