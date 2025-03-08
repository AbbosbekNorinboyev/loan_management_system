package uz.pdp.loan_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.loan_management_system.dto.ErrorDTO;
import uz.pdp.loan_management_system.dto.ResponseDTO;
import uz.pdp.loan_management_system.entity.Transaction;
import uz.pdp.loan_management_system.exception.CustomUserNotFoundException;
import uz.pdp.loan_management_system.exception.ResourceNotFoundException;
import uz.pdp.loan_management_system.mapper.AccountMapper;
import uz.pdp.loan_management_system.mapper.TransactionMapper;
import uz.pdp.loan_management_system.repository.TransactionRepository;
import uz.pdp.loan_management_system.request.TransactionRequest;
import uz.pdp.loan_management_system.response.TransactionResponse;
import uz.pdp.loan_management_system.service.TransactionService;
import uz.pdp.loan_management_system.validation.TransactionValidation;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionValidation transactionValidation;
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    @Override
    public ResponseDTO<TransactionResponse> createTransaction(TransactionRequest transactionRequest) {
        List<ErrorDTO> errors = transactionValidation.validate(transactionRequest);
        if (!errors.isEmpty()) {
            return ResponseDTO.<TransactionResponse>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Validation error")
                    .success(false)
                    .build();
        }
        Transaction transaction = transactionMapper.toEntity(transactionRequest);
        transactionRepository.save(transaction);
        return ResponseDTO.<TransactionResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Transaction successfully saved")
                .success(true)
                .data(transactionMapper.toResponse(transaction))
                .build();
    }

    @Override
    public ResponseDTO<TransactionResponse> getTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + transactionId));
        return ResponseDTO.<TransactionResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Transaction successfully found")
                .success(true)
                .data(transactionMapper.toResponse(transaction))
                .build();
    }

    @Override
    public ResponseDTO<List<TransactionResponse>> getAllTransaction() {
        List<Transaction> transactions = transactionRepository.findAll();
        return ResponseDTO.<List<TransactionResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Transaction list successfully saved")
                .success(true)
                .data(transactions.stream().map(transactionMapper::toResponse).toList())
                .build();
    }

    @Override
    public ResponseDTO<Void> updateTransaction(TransactionRequest transactionRequest, Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + transactionId));
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setTransactionType(transactionRequest.getTransactionType());
        transactionRepository.save(transaction);
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Transaction successfully updated")
                .success(true)
                .build();
    }
}
