package uz.pdp.loan_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.loan_management_system.dto.LoanDto;
import uz.pdp.loan_management_system.dto.response.TransactionResponse;
import uz.pdp.loan_management_system.entity.Account;
import uz.pdp.loan_management_system.entity.AuthUser;
import uz.pdp.loan_management_system.entity.Transaction;
import uz.pdp.loan_management_system.exception.ResourceNotFoundException;
import uz.pdp.loan_management_system.repository.AuthUserRepository;
import uz.pdp.loan_management_system.dto.request.AccountRequest;
import uz.pdp.loan_management_system.dto.response.AccountResponse;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    private final AuthUserRepository authUserRepository;

    public Account toEntity(AccountRequest accountRequest) {
        AuthUser authUser = authUserRepository.findById(accountRequest.getAuthUserId())
                .orElseThrow(() -> new ResourceNotFoundException("AuthUser not found: " + accountRequest.getAuthUserId()));
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
                .id(account.getId())
                .balance(account.getBalance())
                .accountType(account.getAccountType())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .authUserId(account.getAuthUser().getId())
                .build();
    }

    public List<AccountResponse> dtoList(List<Account> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }
}
