package uz.brb.loan_management_system.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HistoryRequest {
    private Long loanId;
    private BigDecimal amount;
    private LocalDateTime paidAt;
    private Boolean successful;
}
