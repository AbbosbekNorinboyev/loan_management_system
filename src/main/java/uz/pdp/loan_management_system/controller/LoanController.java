package uz.pdp.loan_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.loan_management_system.dto.ResponseDTO;
import uz.pdp.loan_management_system.request.LoanRequest;
import uz.pdp.loan_management_system.response.LoanResponse;
import uz.pdp.loan_management_system.service.LoanService;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping("/create")
    public ResponseDTO<LoanResponse> createLoan(@RequestBody LoanRequest loanRequest) {
        return loanService.createLoan(loanRequest);
    }

    @GetMapping("/{loanId}")
    public ResponseDTO<LoanResponse> getLoan(@PathVariable Long loanId) {
        return loanService.getLoan(loanId);
    }

    @GetMapping
    public ResponseDTO<List<LoanResponse>> getAllLoan() {
        return loanService.getAllLoan();
    }

    @PutMapping("/update/{loanId}")
    public ResponseDTO<Void> updateLoan(@RequestBody LoanRequest loanRequest, @PathVariable Long loanId) {
        return loanService.updateLoan(loanRequest, loanId);
    }
}
