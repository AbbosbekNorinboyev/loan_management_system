package uz.brb.loan_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.LoanApplicationRequest;
import uz.brb.loan_management_system.entity.LoanApplication;
import uz.brb.loan_management_system.exception.ResourceNotFoundException;
import uz.brb.loan_management_system.mapper.LoanMapper;
import uz.brb.loan_management_system.repository.LoanApplicationRepository;
import uz.brb.loan_management_system.service.LoanApplicationService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.brb.loan_management_system.util.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class LoanApplicationServiceImpl implements LoanApplicationService {
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanMapper loanMapper;
    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationServiceImpl.class);

    @Override
    public Response createLoan(LoanApplicationRequest loanApplicationRequest) {
        LoanApplication loanApplication = loanMapper.toEntity(loanApplicationRequest);
        logger.info("Loan successfully saved");
        loanApplicationRepository.save(loanApplication);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Loan successfully saved")
                .success(true)
                .data(loanMapper.toResponse(loanApplication))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response getLoan(Long loanId) {
        LoanApplication loanApplication = loanApplicationRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found: " + loanId));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Loan successfully found")
                .success(true)
                .data(loanMapper.toResponse(loanApplication))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response getAllLoan() {
        List<LoanApplication> loans = loanApplicationRepository.findAll();
        logger.info("Loan list successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Loan list successfully found")
                .success(true)
                .data(loanMapper.dtoList(loans))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response updateLoan(LoanApplicationRequest loanApplicationRequest, Long loanId) {
        LoanApplication loan = loanApplicationRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found: " + loanId));
        loanMapper.update(loan, loanApplicationRequest);
        loanApplicationRepository.save(loan);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("LoanApplication successfully updated")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
