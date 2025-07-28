package uz.pdp.loan_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.loan_management_system.dto.Response;
import uz.pdp.loan_management_system.dto.request.ClientRequest;
import uz.pdp.loan_management_system.service.ClientService;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/create")
    public Response createClient(@RequestBody ClientRequest clientRequest) {
        return clientService.createClient(clientRequest);
    }

    @GetMapping("/get")
    public Response getClient(@RequestParam("clientId") Long clientId) {
        return clientService.getClient(clientId);
    }

    @GetMapping
    public Response getAllClient() {
        return clientService.getAllClient();
    }

    @PutMapping("/update")
    public Response updateClient(@RequestParam("clientId") ClientRequest clientRequest,
                                 @PathVariable Long clientId) {
        return clientService.updateClient(clientRequest, clientId);
    }
}
