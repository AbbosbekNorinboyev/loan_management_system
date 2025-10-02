package uz.brb.loan_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.brb.loan_management_system.dto.LoanDto;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.AccountRequest;
import uz.brb.loan_management_system.entity.Account;
import uz.brb.loan_management_system.enums.AccountType;
import uz.brb.loan_management_system.exception.ResourceNotFoundException;
import uz.brb.loan_management_system.mapper.AccountMapper;
import uz.brb.loan_management_system.mapper.interfaces.AccountMapperInterface;
import uz.brb.loan_management_system.repository.AccountRepository;
import uz.brb.loan_management_system.service.AccountService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.brb.loan_management_system.util.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;
    private final AccountMapperInterface accountMapperInterface;
    private final AccountRepository accountRepository;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public Response createAccount(AccountRequest accountRequest) {
        Account account = accountMapper.toEntity(accountRequest);
        accountRepository.save(account);
        logger.info("Account successfully saved");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Account successfully saved")
                .success(true)
                .data(accountMapper.toResponse(account))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountId));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Account successfully found")
                .success(true)
                .data(accountMapperInterface.toAccountResponse(account))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response getAllAccount() {
        List<Account> accounts = accountRepository.findAll();
        logger.info("Account list successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Account list successfully saved")
                .success(true)
                .data(accountMapper.dtoList(accounts))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response updateAccount(AccountRequest accountRequest, Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountId));
        accountMapper.update(account, accountRequest);
        account.setUpdatedAt(accountRequest.getUpdatedAt());
        accountRepository.save(account);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Account successfully updated")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response getAccountsGroupedByTypeStats(AccountType accountType) {
        String accountTypeString = accountType != null ? accountType.name() : null;
        List<Object[]> rawResults = accountRepository.findAccountByAccountType(accountTypeString);
        System.out.println("rawResults = " + rawResults);
        List<LoanDto> loanDtoList = rawResults.stream()
                .map(row -> new LoanDto(
                        row[0] != null ? row[0].toString() : null,
                        (int) (row[1] != null ? ((Number) row[1]).longValue() : 0L),  // COUNT(*)
                        row[2] != null ? ((Number) row[2]).doubleValue() : 0.0 // SUM(balance)
                )).toList();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Account successfully found")
                .success(true)
                .data(loanDtoList)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
