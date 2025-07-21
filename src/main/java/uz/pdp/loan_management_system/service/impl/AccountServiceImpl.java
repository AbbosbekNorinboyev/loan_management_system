package uz.pdp.loan_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.loan_management_system.dto.ErrorDTO;
import uz.pdp.loan_management_system.dto.ResponseDTO;
import uz.pdp.loan_management_system.entity.Account;
import uz.pdp.loan_management_system.exception.ResourceNotFoundException;
import uz.pdp.loan_management_system.mapper.AccountMapper;
import uz.pdp.loan_management_system.mapper.interfaces.AccountMapperInterface;
import uz.pdp.loan_management_system.repository.AccountRepository;
import uz.pdp.loan_management_system.dto.request.AccountRequest;
import uz.pdp.loan_management_system.dto.response.AccountResponse;
import uz.pdp.loan_management_system.service.AccountService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;
    private final AccountMapperInterface accountMapperInterface;
    private final AccountRepository accountRepository;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public ResponseDTO<AccountResponse> createAccount(AccountRequest accountRequest) {
        Account account = accountMapper.toEntity(accountRequest);
        accountRepository.save(account);
        logger.info("Account successfully saved");
        return ResponseDTO.<AccountResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Account successfully saved")
                .success(true)
                .data(accountMapper.toResponse(account))
                .build();
    }

    @Override
    public ResponseDTO<AccountResponse> getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountId));
        return ResponseDTO.<AccountResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Account successfully found")
                .success(true)
                .data(accountMapperInterface.toAccountResponse(account))
                .build();
    }

    @Override
    public ResponseDTO<List<AccountResponse>> getAllAccount() {
        List<Account> accounts = accountRepository.findAll();
        logger.info("Account list successfully found");
        return ResponseDTO.<List<AccountResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Account list successfully saved")
                .success(true)
                .data(accounts.stream().map(accountMapper::toResponse).toList())
                .build();
    }

    @Override
    public ResponseDTO<Void> updateAccount(AccountRequest accountRequest, Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountId));
        account.setBalance(accountRequest.getBalance());
        account.setAccountType(accountRequest.getAccountType());
        account.setUpdatedAt(accountRequest.getUpdatedAt());
        accountRepository.save(account);
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Account successfully updated")
                .success(true)
                .build();
    }
}
