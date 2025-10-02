package uz.brb.loan_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.brb.loan_management_system.dto.request.TransactionRequest;
import uz.brb.loan_management_system.dto.response.TransactionResponse;
import uz.brb.loan_management_system.entity.Account;
import uz.brb.loan_management_system.entity.Transaction;
import uz.brb.loan_management_system.exception.ResourceNotFoundException;
import uz.brb.loan_management_system.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionMapper {
    private final AccountRepository accountRepository;

    public Transaction toEntity(TransactionRequest transactionRequest) {
        Account account = accountRepository.findById(transactionRequest.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + transactionRequest.getAccountId()));
        return Transaction.builder()
                .transactionType(transactionRequest.getTransactionType())
                .amount(transactionRequest.getAmount())
                .account(account)
                .createdAt(transactionRequest.getCreatedAt())
                .build();
    }

    public TransactionResponse toResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .accountId(transaction.getAccount().getId())
                .createdAt(transaction.getCreatedAt())
                .build();
    }

    public List<TransactionResponse> dtoList(List<Transaction> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(Transaction entity, TransactionRequest request) {
        if (request == null) {
            return;
        }
        if (request.getAmount() != null) {
            entity.setAmount(request.getAmount());
        }
        if (request.getTransactionType() != null) {
            entity.setTransactionType(request.getTransactionType());
        }
    }
}
