package uz.brb.loan_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.TransactionRequest;
import uz.brb.loan_management_system.service.TransactionService;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/create")
    public Response createTransaction(@RequestBody TransactionRequest transactionRequest) {
        return transactionService.createTransaction(transactionRequest);
    }

    @GetMapping("/get")
    public Response getTransaction(@RequestParam("transactionId") Long transactionId) {
        return transactionService.getTransaction(transactionId);
    }

    @GetMapping("/getAll")
    public Response getAllTransaction() {
        return transactionService.getAllTransaction();
    }

    @GetMapping("/getTransactionByAccountId")
    public Response getTransactionByAccountId(@RequestParam("/accountId") Long accountId) {
        return transactionService.getTransactionByAccountId(accountId);
    }
}
