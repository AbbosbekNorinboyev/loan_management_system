package uz.pdp.loan_management_system.service;

import uz.pdp.loan_management_system.dto.ResponseDTO;
import uz.pdp.loan_management_system.dto.request.AccountRequest;
import uz.pdp.loan_management_system.dto.response.AccountResponse;

import java.util.List;

public interface AccountService {
    ResponseDTO<AccountResponse> createAccount(AccountRequest accountRequest);

    ResponseDTO<AccountResponse> getAccount(Long accountId);

    ResponseDTO<List<AccountResponse>> getAllAccount();

    ResponseDTO<Void> updateAccount(AccountRequest accountRequest, Long accountId);
}
