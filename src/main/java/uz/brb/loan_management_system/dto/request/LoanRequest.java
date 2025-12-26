package uz.brb.loan_management_system.dto.request;

import lombok.*;
import uz.brb.loan_management_system.enums.LoanStatus;

import java.math.BigDecimal;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoanRequest {
    private Long loanApplicationId;
    private BigDecimal principal;
    private BigDecimal interestRate;
    private Integer tenureMonths;
    private BigDecimal outstandingAmount;
    private LoanStatus status;
    private Long userId;
}
