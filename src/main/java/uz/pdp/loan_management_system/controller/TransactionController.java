package uz.pdp.loan_management_system.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.loan_management_system.dto.ResponseDTO;
import uz.pdp.loan_management_system.request.LoanRequest;
import uz.pdp.loan_management_system.request.TransactionRequest;
import uz.pdp.loan_management_system.response.LoanResponse;
import uz.pdp.loan_management_system.response.TransactionResponse;
import uz.pdp.loan_management_system.service.impl.TransactionServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionServiceImpl transactionService;

    @PostMapping("/create")
    public ResponseDTO<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        return transactionService.createTransaction(transactionRequest);
    }

    @GetMapping("/{transactionId}")
    public ResponseDTO<TransactionResponse> getTransaction(@PathVariable Long transactionId) {
        return transactionService.getTransaction(transactionId);
    }

    @GetMapping
    public ResponseDTO<List<TransactionResponse>> getAllTransaction() {
        return transactionService.getAllTransaction();
    }

    @PutMapping("/update/{transactionId}")
    public ResponseDTO<Void> updateTransaction(@RequestBody TransactionRequest transactionRequest,
                                               @PathVariable Long transactionId) {
        return transactionService.updateTransaction(transactionRequest, transactionId);
    }
}
