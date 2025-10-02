package uz.brb.loan_management_system.service;

import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.ClientRequest;

public interface ClientService {
    Response createClient(ClientRequest clientRequest);

    Response getClient(Long clientId);

    Response getAllClient();

    Response updateClient(ClientRequest clientRequest, Long clientId);

    Response getClientByPhoneNumber(String phoneNumber);
}
