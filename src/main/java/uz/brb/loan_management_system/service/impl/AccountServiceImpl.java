package uz.brb.loan_management_system.service.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import uz.brb.loan_management_system.dto.LoanDto;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.AccountRequest;
import uz.brb.loan_management_system.entity.Account;
import uz.brb.loan_management_system.entity.AuthUser;
import uz.brb.loan_management_system.enums.AccountType;
import uz.brb.loan_management_system.exception.ResourceNotFoundException;
import uz.brb.loan_management_system.mapper.AccountMapper;
import uz.brb.loan_management_system.repository.AccountRepository;
import uz.brb.loan_management_system.repository.AuthUserRepository;
import uz.brb.loan_management_system.service.AccountService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.brb.loan_management_system.util.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final AuthUserRepository authUserRepository;
    private final EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public Response createAccount(AccountRequest accountRequest) {
        AuthUser authUser = authUserRepository.findById(accountRequest.getAuthUserId())
                .orElseThrow(() -> new ResourceNotFoundException("AuthUser not found: " + accountRequest.getAuthUserId()));

        Account account = accountMapper.toEntity(accountRequest);
        account.setAuthUser(authUser);
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
                .data(accountMapper.toResponse(account))
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

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Response withdraw(Long id, Double amount) {
        // START
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
        System.out.println("START balance = " + account.getBalance());

        if (account.getBalance() < amount) {
            throw new RuntimeException("Not enough balance");
        }

        // PROCESS
        account.setBalance(account.getBalance() - amount);
        account.setUpdatedAt(LocalDateTime.now());
        accountRepository.saveAndFlush(account);

        sleep(5000); // boshqa transaction aralashishi uchun

        // FINISH
        System.out.println("FINISH balance = " + account.getBalance());

        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Money withdraw successfully")
                .success(true)
                .data(accountMapper.toResponse(account))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ignored) {
        }
    }

    // Bitta transaction ichida bir xil SELECT har xil natija berishini ko‘rish.
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Response readTwice(Long id) {
        Account a1 = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
        System.out.println("First read = " + a1.getBalance());

        sleep(5000);

        entityManager.clear();

        Account a2 = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
        System.out.println("Second read = " + a2.getBalance());

        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Second read successfully")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    // Barqaror o'qish
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Response readTwiceRepeatable(Long id) {
        Account a1 = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
        System.out.println("First = " + a1.getBalance());

        sleep(5000);

        Account a2 = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
        System.out.println("Second = " + a2.getBalance());

        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Second read successfully")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Response phantomTest() {
        List<Account> list1 = accountRepository.findAll();
        System.out.println("Count1 = " + list1.size());

        sleep(5000);  // bu vaqt ichida boshqa transaction yangi row qo‘shishi mumkin

        // CLEAR persistence context to force DB read (Hibernate cache sababli)
        entityManager.clear(); // agar EntityManager qo‘shilgan bo‘lsa

        List<Account> list2 = accountRepository.findAll();
        System.out.println("Count2 = " + list2.size());

        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Phantom read test done")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Response serializableTest(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));

        if (account.getBalance() < 101) {
            throw new RuntimeException("Not enough balance");
        }

        account.setBalance(account.getBalance() - 100);
        accountRepository.saveAndFlush(account);

        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Balance successfully updated with SERIALIZABLE isolation")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
