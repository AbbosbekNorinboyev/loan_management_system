package uz.brb.loan_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.brb.loan_management_system.enums.TransactionType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TransactionRequest {
    @NotBlank(message = "transactionType can not be null or empty")
    private TransactionType transactionType;
    @NotBlank(message = "amount can not be null or empty")
    private Double amount;
    @NotBlank(message = "accountId can not be null or empty")
    private Long accountId;
    private LocalDateTime createdAt;
}