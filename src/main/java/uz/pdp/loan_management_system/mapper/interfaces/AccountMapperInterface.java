package uz.pdp.loan_management_system.mapper.interfaces;

import org.mapstruct.Mapper;
import uz.pdp.loan_management_system.entity.Account;
import uz.pdp.loan_management_system.request.AccountRequest;
import uz.pdp.loan_management_system.response.AccountResponse;

@Mapper(componentModel = "spring")
public interface AccountMapperInterface {
    Account toAccountEntity(AccountRequest accountRequest);
    AccountResponse toAccountResponse(Account account);
}
