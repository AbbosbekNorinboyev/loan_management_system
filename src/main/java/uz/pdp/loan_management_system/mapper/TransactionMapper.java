package uz.pdp.loan_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.loan_management_system.entity.Account;
import uz.pdp.loan_management_system.entity.Transaction;
import uz.pdp.loan_management_system.exception.ResourceNotFoundException;
import uz.pdp.loan_management_system.repository.AccountRepository;
import uz.pdp.loan_management_system.dto.request.TransactionRequest;
import uz.pdp.loan_management_system.dto.response.TransactionResponse;

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
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .accountId(transaction.getAccount().getId())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
