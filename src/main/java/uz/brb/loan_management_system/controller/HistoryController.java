package uz.brb.loan_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.HistoryRequest;
import uz.brb.loan_management_system.service.HistoryService;

@RestController
@RequestMapping("/api/v1/histories")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @PostMapping("/create")
    public Response create(@RequestBody HistoryRequest request) {
        return historyService.create(request);
    }

    @GetMapping("/getAll")
    public Response getAll() {
        return historyService.getAllHistory();
    }
}
