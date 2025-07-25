package uz.pdp.loan_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.loan_management_system.dto.Response;
import uz.pdp.loan_management_system.dto.request.LoanRequest;
import uz.pdp.loan_management_system.service.LoanService;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping("/create")
    public Response createLoan(@Valid @RequestBody LoanRequest loanRequest) {
        return loanService.createLoan(loanRequest);
    }

    @GetMapping("/get")
    public Response getLoan(@RequestParam("loanId") Long loanId) {
        return loanService.getLoan(loanId);
    }

    @GetMapping
    public Response getAllLoan() {
        return loanService.getAllLoan();
    }

    @PutMapping("/update")
    public Response updateLoan(@RequestParam LoanRequest loanRequest,
                               @RequestParam("loanId") Long loanId) {
        return loanService.updateLoan(loanRequest, loanId);
    }
}
