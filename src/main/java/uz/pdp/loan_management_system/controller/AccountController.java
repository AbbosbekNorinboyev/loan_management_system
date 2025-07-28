package uz.pdp.loan_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.loan_management_system.dto.Response;
import uz.pdp.loan_management_system.dto.request.AccountRequest;
import uz.pdp.loan_management_system.enums.AccountType;
import uz.pdp.loan_management_system.service.AccountService;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public Response createAccount(@RequestBody AccountRequest accountRequest) {
        return accountService.createAccount(accountRequest);
    }

    @GetMapping("/get")
    public Response getAccount(@RequestParam("accountId") Long accountId) {
        return accountService.getAccount(accountId);
    }

    @GetMapping
    public Response getAllAccount() {
        return accountService.getAllAccount();
    }

    @PutMapping("/update")
    public Response updateAccount(@RequestBody AccountRequest accountRequest,
                                  @RequestParam("accountId") Long accountId) {
        return accountService.updateAccount(accountRequest, accountId);
    }

    @GetMapping("/getAccountByAccountType")
    public Response getAccountByAccountType(@RequestParam(value = "accountType", required = false) AccountType accountType) {
        return accountService.getAccountsGroupedByTypeStats(accountType);
    }
}
