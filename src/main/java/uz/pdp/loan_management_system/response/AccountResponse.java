package uz.pdp.loan_management_system.response;

import lombok.*;
import uz.pdp.loan_management_system.enums.AccountType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountResponse {
    private Double balance;
    private AccountType accountType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long authUserId;
}
