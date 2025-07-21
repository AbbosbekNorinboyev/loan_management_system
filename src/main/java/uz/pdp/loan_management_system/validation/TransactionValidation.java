package uz.pdp.loan_management_system.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.loan_management_system.dto.ErrorDTO;
import uz.pdp.loan_management_system.dto.request.TransactionRequest;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionValidation {
    public List<ErrorDTO> validate(TransactionRequest transactionRequest){
        List<ErrorDTO> errors = new ArrayList<>();
        if (transactionRequest.getAmount()<0){
            errors.add(new ErrorDTO("amount", "amount can not be negative"));
        }
        return errors;
    }
}
