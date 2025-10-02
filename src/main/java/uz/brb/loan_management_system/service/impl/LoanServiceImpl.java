package uz.brb.loan_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.LoanRequest;
import uz.brb.loan_management_system.entity.Loan;
import uz.brb.loan_management_system.exception.ResourceNotFoundException;
import uz.brb.loan_management_system.mapper.LoanMapper;
import uz.brb.loan_management_system.mapper.interfaces.LoanMapperInterface;
import uz.brb.loan_management_system.repository.LoanRepository;
import uz.brb.loan_management_system.service.LoanService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.brb.loan_management_system.util.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final LoanMapperInterface loanMapperInterface;
    private static final Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Override
    public Response createLoan(LoanRequest loanRequest) {
        Loan loan = loanMapper.toEntity(loanRequest);
        logger.info("Loan successfully saved");
        loanRepository.save(loan);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Loan successfully saved")
                .success(true)
                .data(loanMapper.toResponse(loan))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response getLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found: " + loanId));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Loan successfully found")
                .success(true)
                .data(loanMapperInterface.toLoanResponse(loan))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response getAllLoan() {
        List<Loan> loans = loanRepository.findAll();
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
    public Response updateLoan(LoanRequest loanRequest, Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found: " + loanId));
        loanMapper.update(loan, loanRequest);
        loanRepository.save(loan);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Loan successfully updated")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
