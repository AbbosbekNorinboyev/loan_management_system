package uz.pdp.loan_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.loan_management_system.enums.AccountType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountRequest {
    @NotBlank(message = "balance can not be null or empty")
    private Double balance;
    @NotBlank(message = "accountType can not be null or empty")
    private AccountType accountType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @NotBlank(message = "authUserId can not be null or empty")
    private Long authUserId;
}
