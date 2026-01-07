package uz.brb.loan_management_system.dto.response;

import lombok.*;
import uz.brb.loan_management_system.enums.LoanApplicationStatus;

import java.math.BigDecimal;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoanApplicationResponse {
    private Long id;
    private BigDecimal amount;
    private Integer tenureMonths;
    private LoanApplicationStatus status;
    private Long authUserId;
}