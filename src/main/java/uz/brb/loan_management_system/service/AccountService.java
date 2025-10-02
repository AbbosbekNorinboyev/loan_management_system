package uz.brb.loan_management_system.service;

import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.AccountRequest;
import uz.brb.loan_management_system.enums.AccountType;

public interface AccountService {
    Response createAccount(AccountRequest accountRequest);

    Response getAccount(Long accountId);

    Response getAllAccount();

    Response updateAccount(AccountRequest accountRequest, Long accountId);

    Response getAccountsGroupedByTypeStats(AccountType accountType);
}
