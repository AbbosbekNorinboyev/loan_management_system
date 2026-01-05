package uz.brb.loan_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.HistoryRequest;
import uz.brb.loan_management_system.entity.History;
import uz.brb.loan_management_system.entity.Loan;
import uz.brb.loan_management_system.exception.ResourceNotFoundException;
import uz.brb.loan_management_system.mapper.HistoryMapper;
import uz.brb.loan_management_system.repository.HistoryRepository;
import uz.brb.loan_management_system.repository.LoanRepository;
import uz.brb.loan_management_system.service.HistoryService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.brb.loan_management_system.util.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryMapper historyMapper;
    private final HistoryRepository historyRepository;
    private final LoanRepository loanRepository;

    @Override
    public Response create(HistoryRequest request) {
        Loan loan = loanRepository.findById(request.getLoanId())
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found: " + request.getLoanId()));

        History history = historyMapper.toEntity(request);
        history.setLoan(loan);
        historyRepository.save(history);

        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Card successfully created")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response getAllHistory() {
        List<History> histories = historyRepository.findAll();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Card list successfully found")
                .data(historyMapper.responseList(histories))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
