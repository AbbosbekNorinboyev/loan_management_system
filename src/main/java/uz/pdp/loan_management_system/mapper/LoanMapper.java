package uz.pdp.loan_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.loan_management_system.entity.AuthUser;
import uz.pdp.loan_management_system.entity.Loan;
import uz.pdp.loan_management_system.exception.CustomUserNotFoundException;
import uz.pdp.loan_management_system.repository.AuthUserRepository;
import uz.pdp.loan_management_system.request.LoanRequest;
import uz.pdp.loan_management_system.response.LoanResponse;

@Component
@RequiredArgsConstructor
public class LoanMapper {

    private final AuthUserRepository authUserRepository;

    public Loan toEntity(LoanRequest loanRequest) {
        AuthUser authUser = authUserRepository.findById(loanRequest.getAuthUserId())
                .orElseThrow(() -> new CustomUserNotFoundException("AuthUser not found: " + loanRequest.getAuthUserId()));
        Loan loan = new Loan();
        loan.setLoanName(loanRequest.getLoanName());
        loan.setLoanAmount(loanRequest.getLoanAmount());
        loan.setStatus(loanRequest.getStatus());
        loan.setAuthUser(authUser);
        return loan;
    }

    public LoanResponse toResponse(Loan loan) {
        return LoanResponse.builder()
                .loanName(loan.getLoanName())
                .loanAmount(loan.getLoanAmount())
                .status(loan.getStatus())
                .authUserId(loan.getAuthUser().getId())
                .build();
    }
}
