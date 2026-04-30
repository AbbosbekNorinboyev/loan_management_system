package uz.brb.loan_management_system.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.ArrayList;
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
    @Transactional(readOnly = true)
    public Response getAllHistory(Long loanId) {
        Specification<History> spec = (root, q, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (loanId != null) {
                predicates.add(cb.equal(root.get("loan").get("id"), loanId));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        List<History> histories = historyRepository.findAll(spec);
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
