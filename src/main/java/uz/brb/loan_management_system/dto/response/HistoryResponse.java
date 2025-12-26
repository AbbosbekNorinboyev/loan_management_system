package uz.brb.loan_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class HistoryResponse {
    private Long id;
    private Long loanId;
    private BigDecimal amount;
    private LocalDateTime paidAt;
    private Boolean successful;
}
