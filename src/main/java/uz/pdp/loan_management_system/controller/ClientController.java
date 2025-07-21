package uz.pdp.loan_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.loan_management_system.dto.ResponseDTO;
import uz.pdp.loan_management_system.dto.request.ClientRequest;
import uz.pdp.loan_management_system.dto.response.ClientResponse;
import uz.pdp.loan_management_system.service.impl.ClientServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientServiceImpl clientService;

    @PostMapping("/create")
    public ResponseDTO<ClientResponse> createClient(@RequestBody ClientRequest clientRequest) {
        return clientService.createClient(clientRequest);
    }

    @GetMapping("/{clientId}")
    public ResponseDTO<ClientResponse> getClient(@PathVariable Long clientId) {
        return clientService.getClient(clientId);
    }

    @GetMapping
    public ResponseDTO<List<ClientResponse>> getAllClient() {
        return clientService.getAllClient();
    }

    @PutMapping("/update/{clientId}")
    public ResponseDTO<Void> updateClient(@RequestBody ClientRequest clientRequest, @PathVariable Long clientId) {
        return clientService.updateClient(clientRequest, clientId);
    }
}
