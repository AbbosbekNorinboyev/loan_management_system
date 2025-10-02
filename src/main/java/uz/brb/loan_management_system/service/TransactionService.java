package uz.brb.loan_management_system.service;

import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.TransactionRequest;

public interface TransactionService {
    Response createTransaction(TransactionRequest transactionRequest);

    Response getTransaction(Long transactionId);

    Response getAllTransaction();

    Response updateTransaction(TransactionRequest transactionRequest, Long transactionId);

    Response getTransactionByAccountId(Long accountId);
}
