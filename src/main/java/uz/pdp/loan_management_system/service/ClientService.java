package uz.pdp.loan_management_system.service;

import uz.pdp.loan_management_system.dto.ResponseDTO;
import uz.pdp.loan_management_system.request.ClientRequest;
import uz.pdp.loan_management_system.response.ClientResponse;

import java.util.List;

public interface ClientService {
    ResponseDTO<ClientResponse> createClient(ClientRequest clientRequest);

    ResponseDTO<ClientResponse> getClient(Long clientId);

    ResponseDTO<List<ClientResponse>> getAllClient();

    ResponseDTO<Void> updateClient(ClientRequest clientRequest, Long clientId);
}
