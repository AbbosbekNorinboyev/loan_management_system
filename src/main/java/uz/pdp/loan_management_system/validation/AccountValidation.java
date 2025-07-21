package uz.pdp.loan_management_system.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.loan_management_system.dto.ErrorDTO;
import uz.pdp.loan_management_system.dto.request.AccountRequest;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountValidation {
    public List<ErrorDTO> validate(AccountRequest accountRequest) {
        List<ErrorDTO> errors = new ArrayList<>();
        if (accountRequest.getBalance() < 0) {
            errors.add(new ErrorDTO("balance", "balance can not be negative"));
        }
        return errors;
    }
}
