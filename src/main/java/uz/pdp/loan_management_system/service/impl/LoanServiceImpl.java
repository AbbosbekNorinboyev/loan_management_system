package uz.pdp.loan_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.loan_management_system.dto.ErrorDTO;
import uz.pdp.loan_management_system.exception.ResourceNotFoundException;
import uz.pdp.loan_management_system.mapper.LoanMapper;
import uz.pdp.loan_management_system.mapper.interfaces.LoanMapperInterface;
import uz.pdp.loan_management_system.dto.request.LoanRequest;
import uz.pdp.loan_management_system.dto.ResponseDTO;
import uz.pdp.loan_management_system.entity.Loan;
import uz.pdp.loan_management_system.repository.LoanRepository;
import uz.pdp.loan_management_system.dto.response.LoanResponse;
import uz.pdp.loan_management_system.service.LoanService;
import uz.pdp.loan_management_system.validation.LoanValidation;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanValidation loanValidation;
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final LoanMapperInterface loanMapperInterface;
    private static final Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Override
    public ResponseDTO<LoanResponse> createLoan(LoanRequest loanRequest) {
        List<ErrorDTO> errors = loanValidation.validate(loanRequest);
        if (!errors.isEmpty()) {
            logger.error("Validation error createLoan");
            return ResponseDTO.<LoanResponse>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Validation error")
                    .success(false)
                    .build();
        }
        Loan loan = loanMapper.toEntity(loanRequest);
        logger.info("Loan successfully saved");
        System.out.println("loan = " + loan);
        loanRepository.save(loan);
        return ResponseDTO.<LoanResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Loan successfully saved")
                .success(true)
                .data(loanMapper.toResponse(loan))
                .build();
    }

    @Override
    public ResponseDTO<LoanResponse> getLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found: " + loanId));
        return ResponseDTO.<LoanResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Loan successfully found")
                .success(true)
                .data(loanMapperInterface.toLoanResponse(loan))
                .build();
    }

    @Override
    public ResponseDTO<List<LoanResponse>> getAllLoan() {
        List<Loan> loans = loanRepository.findAll();
        logger.info("Loan list successfully found");
        return ResponseDTO.<List<LoanResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Loan list successfully found")
                .success(true)
                .data(loans.stream().map(loanMapper::toResponse).toList())
                .build();
    }

    @Override
    public ResponseDTO<Void> updateLoan(LoanRequest loanRequest, Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found: " + loanId));
        loan.setLoanName(loanRequest.getLoanName());
        loan.setLoanAmount(loanRequest.getLoanAmount());
        loan.setStatus(loanRequest.getStatus());
        loanRepository.save(loan);
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Loan successfully updated")
                .success(true)
                .build();
    }
}
