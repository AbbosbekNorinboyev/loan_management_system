package uz.pdp.loan_management_system.mapper.interfaces;

import org.mapstruct.Mapper;
import uz.pdp.loan_management_system.entity.Loan;
import uz.pdp.loan_management_system.dto.request.LoanRequest;
import uz.pdp.loan_management_system.dto.response.LoanResponse;

@Mapper(componentModel = "spring")
public interface LoanMapperInterface {
    Loan toLoanEntity(LoanRequest loanRequest);
    LoanResponse toLoanResponse(Loan loan);
}
