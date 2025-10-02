package uz.brb.loan_management_system.mapper.interfaces;

import org.mapstruct.Mapper;
import uz.brb.loan_management_system.entity.Transaction;
import uz.brb.loan_management_system.dto.request.TransactionRequest;
import uz.brb.loan_management_system.dto.response.TransactionResponse;

@Mapper(componentModel = "spring")
public interface TransactionMapperInterface {
    Transaction toTransactionEntity(TransactionRequest transactionRequest);

    TransactionResponse toTransactionResponse(Transaction transaction);
}
