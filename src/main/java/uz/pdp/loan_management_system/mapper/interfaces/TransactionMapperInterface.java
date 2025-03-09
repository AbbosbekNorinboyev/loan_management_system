package uz.pdp.loan_management_system.mapper.interfaces;

import org.mapstruct.Mapper;
import uz.pdp.loan_management_system.entity.Transaction;
import uz.pdp.loan_management_system.request.TransactionRequest;
import uz.pdp.loan_management_system.response.TransactionResponse;

@Mapper(componentModel = "spring")
public interface TransactionMapperInterface {
    Transaction toTransactionEntity(TransactionRequest transactionRequest);

    TransactionResponse toTransactionResponse(Transaction transaction);
}
