package uz.brb.loan_management_system.service;

import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.HistoryRequest;

public interface HistoryService {
    Response create(HistoryRequest request);

    Response getAllHistory();
}