package uz.brb.loan_management_system.service;

import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.LoanRequest;

public interface LoanService {
    Response createLoan(LoanRequest loanRequest);

    Response getLoan(Long loanId);

    Response getAllLoan();

    Response updateLoan(LoanRequest loanRequest, Long loanId);
}
