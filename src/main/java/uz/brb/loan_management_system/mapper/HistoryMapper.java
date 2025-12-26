package uz.brb.loan_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.brb.loan_management_system.dto.request.HistoryRequest;
import uz.brb.loan_management_system.dto.response.HistoryResponse;
import uz.brb.loan_management_system.entity.History;

import java.util.ArrayList;
import java.util.List;

@Component
public class HistoryMapper {
    public History toEntity(HistoryRequest request) {
        return History.builder()
                .amount(request.getAmount())
                .paidAt(request.getPaidAt())
                .successful(request.getSuccessful())
                .build();
    }

    public HistoryResponse toResponse(History entity) {
        return HistoryResponse.builder()
                .id(entity.getId())
                .loanId(entity.getLoan() != null ? entity.getLoan().getId() : null)
                .amount(entity.getAmount())
                .paidAt(entity.getPaidAt())
                .successful(entity.getSuccessful())
                .build();
    }

    public List<HistoryResponse> responseList(List<History> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(History entity, HistoryRequest request) {
        if (request == null) {
            return;
        }
        if (request.getAmount() != null) {
            entity.setAmount(request.getAmount());
        }
        if (request.getSuccessful() != null) {
            entity.setSuccessful(request.getSuccessful());
        }
    }
}
