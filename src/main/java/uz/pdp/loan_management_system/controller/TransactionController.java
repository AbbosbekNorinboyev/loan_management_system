package uz.pdp.loan_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.loan_management_system.dto.Response;
import uz.pdp.loan_management_system.dto.request.TransactionRequest;
import uz.pdp.loan_management_system.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/create")
    public Response createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        return transactionService.createTransaction(transactionRequest);
    }

    @GetMapping("/{transactionId}")
    public Response getTransaction(@PathVariable Long transactionId) {
        return transactionService.getTransaction(transactionId);
    }

    @GetMapping
    public Response getAllTransaction() {
        return transactionService.getAllTransaction();
    }

    @PutMapping("/update/{transactionId}")
    public Response updateTransaction(@RequestBody TransactionRequest transactionRequest,
                                      @PathVariable Long transactionId) {
        return transactionService.updateTransaction(transactionRequest, transactionId);
    }
}
