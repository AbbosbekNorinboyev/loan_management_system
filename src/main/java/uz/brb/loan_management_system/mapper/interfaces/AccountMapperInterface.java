package uz.brb.loan_management_system.mapper.interfaces;

import org.mapstruct.Mapper;
import uz.brb.loan_management_system.entity.Account;
import uz.brb.loan_management_system.dto.request.AccountRequest;
import uz.brb.loan_management_system.dto.response.AccountResponse;

@Mapper(componentModel = "spring")
public interface AccountMapperInterface {
    Account toAccountEntity(AccountRequest accountRequest);
    AccountResponse toAccountResponse(Account account);
}
