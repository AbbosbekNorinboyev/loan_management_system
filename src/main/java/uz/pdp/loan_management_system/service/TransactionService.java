package uz.pdp.loan_management_system.service;

import uz.pdp.loan_management_system.dto.ResponseDTO;
import uz.pdp.loan_management_system.dto.request.TransactionRequest;
import uz.pdp.loan_management_system.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    ResponseDTO<TransactionResponse> createTransaction(TransactionRequest transactionRequest);
    ResponseDTO<TransactionResponse> getTransaction(Long transactionId);
    ResponseDTO<List<TransactionResponse>> getAllTransaction();
    ResponseDTO<Void> updateTransaction(TransactionRequest transactionRequest, Long transactionId);
}
