package uz.brb.loan_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.brb.loan_management_system.dto.request.LoanRequest;
import uz.brb.loan_management_system.dto.response.LoanResponse;
import uz.brb.loan_management_system.entity.AuthUser;
import uz.brb.loan_management_system.entity.Loan;
import uz.brb.loan_management_system.exception.ResourceNotFoundException;
import uz.brb.loan_management_system.repository.AuthUserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoanMapper {
    private final AuthUserRepository authUserRepository;

    public Loan toEntity(LoanRequest loanRequest) {
        AuthUser authUser = authUserRepository.findById(loanRequest.getAuthUserId())
                .orElseThrow(() -> new ResourceNotFoundException("AuthUser not found: " + loanRequest.getAuthUserId()));
        Loan loan = new Loan();
        loan.setLoanName(loanRequest.getLoanName());
        loan.setLoanAmount(loanRequest.getLoanAmount());
        loan.setStatus(loanRequest.getStatus());
        loan.setAuthUser(authUser);
        return loan;
    }

    public LoanResponse toResponse(Loan loan) {
        return LoanResponse.builder()
                .id(loan.getId())
                .loanName(loan.getLoanName())
                .loanAmount(loan.getLoanAmount())
                .status(loan.getStatus())
                .authUserId(loan.getAuthUser().getId())
                .build();
    }

    public List<LoanResponse> dtoList(List<Loan> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(Loan entity, LoanRequest request) {
        if (request == null) {
            return;
        }
        if (request.getLoanName() != null && !request.getLoanName().trim().isEmpty()) {
            entity.setLoanName(request.getLoanName());
        }
        if (request.getLoanAmount() != null) {
            entity.setLoanAmount(request.getLoanAmount());
        }
        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            entity.setStatus(request.getStatus());
        }
    }
}
