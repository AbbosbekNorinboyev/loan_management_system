package uz.brb.loan_management_system.service;

import org.springframework.data.domain.Pageable;
import uz.brb.loan_management_system.dto.Response;

import java.time.LocalDateTime;

public interface ApiLogService {
    Response saveLog(String username, String method, String path, LocalDateTime time, long duration);

    Response getAll(Pageable pageable);
}