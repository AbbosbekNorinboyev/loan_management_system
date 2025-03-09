package uz.pdp.loan_management_system.mapper.interfaces;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.pdp.loan_management_system.entity.Loan;
import uz.pdp.loan_management_system.request.LoanRequest;
import uz.pdp.loan_management_system.response.LoanResponse;

@Mapper(componentModel = "spring")
public interface LoanMapperInterface {
    Loan toLoanEntity(LoanRequest loanRequest);
    LoanResponse toLoanResponse(Loan loan);
}
