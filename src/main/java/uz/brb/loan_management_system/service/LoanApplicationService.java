package uz.brb.loan_management_system.service;

import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.LoanApplicationRequest;

public interface LoanApplicationService {
    Response createLoan(LoanApplicationRequest loanApplicationRequest);

    Response getLoan(Long loanId);

    Response getAllLoan();

    Response updateLoan(LoanApplicationRequest loanApplicationRequest, Long loanId);
}
