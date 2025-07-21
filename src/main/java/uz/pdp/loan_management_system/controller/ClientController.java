package uz.pdp.loan_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.loan_management_system.dto.Response;
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
    public Response createClient(@Valid @RequestBody ClientRequest clientRequest) {
        return clientService.createClient(clientRequest);
    }

    @GetMapping("/{clientId}")
    public Response getClient(@PathVariable Long clientId) {
        return clientService.getClient(clientId);
    }

    @GetMapping
    public Response getAllClient() {
        return clientService.getAllClient();
    }

    @PutMapping("/update/{clientId}")
    public Response updateClient(@RequestBody ClientRequest clientRequest,
                                 @PathVariable Long clientId) {
        return clientService.updateClient(clientRequest, clientId);
    }
}
