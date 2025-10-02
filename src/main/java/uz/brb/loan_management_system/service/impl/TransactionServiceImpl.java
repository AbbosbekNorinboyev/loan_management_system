package uz.brb.loan_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.TransactionRequest;
import uz.brb.loan_management_system.entity.Transaction;
import uz.brb.loan_management_system.exception.ResourceNotFoundException;
import uz.brb.loan_management_system.mapper.TransactionMapper;
import uz.brb.loan_management_system.mapper.interfaces.TransactionMapperInterface;
import uz.brb.loan_management_system.repository.TransactionRepository;
import uz.brb.loan_management_system.service.TransactionService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.brb.loan_management_system.util.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionMapper transactionMapper;
    private final TransactionMapperInterface transactionMapperInterface;
    private final TransactionRepository transactionRepository;
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Override
    public Response createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = transactionMapper.toEntity(transactionRequest);
        transactionRepository.save(transaction);
        logger.info("Transaction successfully saved");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Transaction successfully saved")
                .success(true)
                .data(transactionMapper.toResponse(transaction))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response getTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + transactionId));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Transaction successfully found")
                .success(true)
                .data(transactionMapperInterface.toTransactionResponse(transaction))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response getAllTransaction() {
        List<Transaction> transactions = transactionRepository.findAll();
        logger.info("Transaction list successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Transaction list successfully saved")
                .success(true)
                .data(transactionMapper.dtoList(transactions))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response updateTransaction(TransactionRequest transactionRequest, Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + transactionId));
        transactionMapper.update(transaction, transactionRequest);
        transactionRepository.save(transaction);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Transaction successfully updated")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response getTransactionByAccountId(Long accountId) {
        List<Transaction> allByAccountId = transactionRepository.findAllByAccountId(accountId);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Transaction successfully found")
                .success(true)
                .data(transactionMapper.dtoList(allByAccountId))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
