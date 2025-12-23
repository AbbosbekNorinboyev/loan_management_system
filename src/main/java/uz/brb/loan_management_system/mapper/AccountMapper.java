package uz.brb.loan_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.brb.loan_management_system.dto.request.AccountRequest;
import uz.brb.loan_management_system.dto.response.AccountResponse;
import uz.brb.loan_management_system.entity.Account;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMapper {
    public Account toEntity(AccountRequest accountRequest) {
        return Account.builder()
                .balance(accountRequest.getBalance())
                .accountType(accountRequest.getAccountType())
                .createdAt(accountRequest.getCreatedAt())
                .updatedAt(accountRequest.getUpdatedAt())
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

    public void update(Account entity, AccountRequest request) {
        if (request == null) {
            return;
        }
        if (entity.getBalance() != null) {
            entity.setBalance(request.getBalance());
        }
        if (entity.getAccountType() != null) {
            entity.setAccountType(request.getAccountType());
        }
    }
}
