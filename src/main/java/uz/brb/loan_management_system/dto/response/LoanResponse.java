package uz.brb.loan_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.brb.loan_management_system.enums.LoanStatus;

import java.math.BigDecimal;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class LoanResponse {
    private Long id;
    private Long loanApplicationId;
    private BigDecimal principal;
    private BigDecimal interestRate;
    private Integer tenureMonths;
    private BigDecimal outstandingAmount;
    private LoanStatus status;
}
