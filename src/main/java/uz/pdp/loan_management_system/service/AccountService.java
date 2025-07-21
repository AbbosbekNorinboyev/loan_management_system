package uz.pdp.loan_management_system.service;

import uz.pdp.loan_management_system.dto.Response;
import uz.pdp.loan_management_system.dto.request.AccountRequest;

public interface AccountService {
    Response createAccount(AccountRequest accountRequest);

    Response getAccount(Long accountId);

    Response getAllAccount();

    Response updateAccount(AccountRequest accountRequest, Long accountId);
}
