package uz.pdp.loan_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.loan_management_system.dto.ResponseDTO;
import uz.pdp.loan_management_system.request.AccountRequest;
import uz.pdp.loan_management_system.response.AccountResponse;
import uz.pdp.loan_management_system.service.impl.AccountServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountServiceImpl accountService;

    @PostMapping("/create")
    public ResponseDTO<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest) {
        return accountService.createAccount(accountRequest);
    }

    @GetMapping("/{accountId}")
    public ResponseDTO<AccountResponse> getAccount(@PathVariable Long accountId) {
        return accountService.getAccount(accountId);
    }

    @GetMapping
    public ResponseDTO<List<AccountResponse>> getAllAccount() {
        return accountService.getAllAccount();
    }

    @PutMapping("/update/{accountId}")
    public ResponseDTO<Void> updateAccount(@RequestBody AccountRequest accountRequest,
                                           @PathVariable Long accountId) {
        return accountService.updateAccount(accountRequest, accountId);
    }
}
