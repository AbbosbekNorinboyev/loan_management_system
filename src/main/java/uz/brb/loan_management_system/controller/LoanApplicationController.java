package uz.brb.loan_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.LoanApplicationRequest;
import uz.brb.loan_management_system.service.LoanApplicationService;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;

    @PostMapping("/create")
    public Response createLoan(@RequestBody LoanApplicationRequest loanApplicationRequest) {
        return loanApplicationService.createLoan(loanApplicationRequest);
    }

    @GetMapping("/get")
    public Response getLoan(@RequestParam("loanId") Long loanId) {
        return loanApplicationService.getLoan(loanId);
    }

    @GetMapping("/getAll")
    public Response getAllLoan() {
        return loanApplicationService.getAllLoan();
    }

    @PutMapping("/update")
    public Response updateLoan(@RequestParam LoanApplicationRequest loanApplicationRequest,
                               @RequestParam("loanId") Long loanId) {
        return loanApplicationService.updateLoan(loanApplicationRequest, loanId);
    }
}
