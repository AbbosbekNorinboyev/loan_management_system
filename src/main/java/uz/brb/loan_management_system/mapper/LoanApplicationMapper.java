package uz.brb.loan_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.brb.loan_management_system.dto.request.LoanApplicationRequest;
import uz.brb.loan_management_system.dto.response.LoanApplicationResponse;
import uz.brb.loan_management_system.entity.LoanApplication;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoanApplicationMapper {
    public LoanApplication toEntity(LoanApplicationRequest loanApplicationRequest) {
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setRequestedAmount(loanApplicationRequest.getRequestedAmount());
        loanApplication.setTenureMonths(loanApplicationRequest.getTenureMonths());
        loanApplication.setStatus(loanApplicationRequest.getStatus());
        return loanApplication;
    }

    public LoanApplicationResponse toResponse(LoanApplication loanApplication) {
        return LoanApplicationResponse.builder()
                .id(loanApplication.getId())
                .requestedAmount(loanApplication.getRequestedAmount())
                .tenureMonths(loanApplication.getTenureMonths())
                .status(loanApplication.getStatus())
                .authUserId(loanApplication.getAuthUser().getId())
                .build();
    }

    public List<LoanApplicationResponse> dtoList(List<LoanApplication> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(LoanApplication entity, LoanApplicationRequest request) {
        if (request == null) {
            return;
        }
        if (request.getRequestedAmount() != null) {
            entity.setRequestedAmount(request.getRequestedAmount());
        }
        if (request.getTenureMonths() != null) {
            entity.setTenureMonths(request.getTenureMonths());
        }
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }
    }
}
