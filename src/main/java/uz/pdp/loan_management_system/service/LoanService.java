package uz.pdp.loan_management_system.service;

import uz.pdp.loan_management_system.dto.ResponseDTO;
import uz.pdp.loan_management_system.dto.request.LoanRequest;
import uz.pdp.loan_management_system.dto.response.LoanResponse;

import java.util.List;

public interface LoanService {
    ResponseDTO<LoanResponse> createLoan(LoanRequest loanRequest);

    ResponseDTO<LoanResponse> getLoan(Long loanId);

    ResponseDTO<List<LoanResponse>> getAllLoan();

    ResponseDTO<Void> updateLoan(LoanRequest loanRequest, Long loanId);
}
