package uz.brb.loan_management_system.dto.response;

import lombok.*;
import uz.brb.loan_management_system.enums.AccountType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountResponse {
    private Long id;
    private Double balance;
    private AccountType accountType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long authUserId;
}
