package uz.pdp.loan_management_system.dto.response;

import lombok.*;
import uz.pdp.loan_management_system.enums.TransactionType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TransactionResponse {
    private Long id;
    private TransactionType transactionType;
    private Double amount;
    private Long accountId;
    private LocalDateTime createdAt;
}