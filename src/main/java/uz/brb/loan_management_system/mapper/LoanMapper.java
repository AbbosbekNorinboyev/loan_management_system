package uz.brb.loan_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.brb.loan_management_system.dto.request.LoanRequest;
import uz.brb.loan_management_system.dto.response.LoanResponse;
import uz.brb.loan_management_system.entity.Loan;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoanMapper {
    public Loan toEntity(LoanRequest loanRequest) {
        return Loan.builder()
                .principal(loanRequest.getPrincipal())
                .interestRate(loanRequest.getInterestRate())
                .tenureMonths(loanRequest.getTenureMonths())
                .outstandingAmount(loanRequest.getOutstandingAmount())
                .build();
    }

    public LoanResponse toResponse(Loan loan) {
        return LoanResponse.builder()
                .id(loan.getId())
                .loanApplicationId(loan.getApplication() != null ? loan.getApplication().getId() : null)
                .principal(loan.getPrincipal())
                .interestRate(loan.getInterestRate())
                .tenureMonths(loan.getTenureMonths())
                .outstandingAmount(loan.getOutstandingAmount())
                .status(loan.getStatus())
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
        if (request.getPrincipal() != null) {
            entity.setPrincipal(request.getPrincipal());
        }
        if (request.getInterestRate() != null) {
            entity.setInterestRate(request.getInterestRate());
        }
        if (request.getTenureMonths() != null) {
            entity.setTenureMonths(request.getTenureMonths());
        }
        if (request.getOutstandingAmount() != null) {
            entity.setOutstandingAmount(request.getOutstandingAmount());
        }
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }
    }
}
