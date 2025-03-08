package uz.pdp.loan_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.loan_management_system.entity.Account;
import uz.pdp.loan_management_system.entity.AuthUser;
import uz.pdp.loan_management_system.exception.CustomUserNotFoundException;
import uz.pdp.loan_management_system.repository.AuthUserRepository;
import uz.pdp.loan_management_system.request.AccountRequest;
import uz.pdp.loan_management_system.response.AccountResponse;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    private final AuthUserRepository authUserRepository;

    public Account toEntity(AccountRequest accountRequest) {
        AuthUser authUser = authUserRepository.findById(accountRequest.getAuthUserId())
                .orElseThrow(() -> new CustomUserNotFoundException("AuthUser not found: " + accountRequest.getAuthUserId()));
        return Account.builder()
                .balance(accountRequest.getBalance())
                .accountType(accountRequest.getAccountType())
                .createdAt(accountRequest.getCreatedAt())
                .updatedAt(accountRequest.getUpdatedAt())
                .authUser(authUser)
                .build();
    }

    public AccountResponse toResponse(Account account) {
        return AccountResponse.builder()
                .balance(account.getBalance())
                .accountType(account.getAccountType())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .authUserId(account.getAuthUser().getId())
                .build();
    }
}
