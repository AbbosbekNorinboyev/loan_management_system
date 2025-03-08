package uz.pdp.loan_management_system.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.loan_management_system.dto.ErrorDTO;
import uz.pdp.loan_management_system.request.LoanRequest;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoanValidation {
    public List<ErrorDTO> validate(LoanRequest loanRequest) {
        List<ErrorDTO> errors = new ArrayList<>();
        if (loanRequest.getLoanAmount() < 0) {
            errors.add(new ErrorDTO("loanAmount", "loanAmount can not be negative"));
        }
        return errors;
    }
}
