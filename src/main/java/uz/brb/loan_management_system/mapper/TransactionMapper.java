package uz.brb.loan_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.brb.loan_management_system.dto.request.TransactionRequest;
import uz.brb.loan_management_system.dto.response.TransactionResponse;
import uz.brb.loan_management_system.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionMapper {
    public Transaction toEntity(TransactionRequest transactionRequest) {
        return Transaction.builder()
                .transactionType(transactionRequest.getTransactionType())
                .amount(transactionRequest.getAmount())
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
