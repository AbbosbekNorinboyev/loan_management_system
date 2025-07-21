package uz.pdp.loan_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.loan_management_system.dto.Response;
import uz.pdp.loan_management_system.dto.request.ClientRequest;
import uz.pdp.loan_management_system.entity.Client;
import uz.pdp.loan_management_system.exception.CustomException;
import uz.pdp.loan_management_system.exception.ResourceNotFoundException;
import uz.pdp.loan_management_system.mapper.ClientMapper;
import uz.pdp.loan_management_system.mapper.interfaces.ClientMapperInterface;
import uz.pdp.loan_management_system.repository.ClientRepository;
import uz.pdp.loan_management_system.service.ClientService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ClientMapperInterface clientMapperInterface;
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Override
    public Response createClient(ClientRequest clientRequest) {
        Client client = clientMapper.toEntity(clientRequest);
        clientRepository.save(client);
        logger.info("Client successfully saved");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Client successfully saved")
                .success(true)
                .data(clientMapper.toResponse(client))
                .build();
    }

    @Override
    public Response getClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Client not found: " + clientId));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Client successfully found")
                .success(true)
                .data(clientMapperInterface.toClientResponse(client))
                .build();
    }

    @Override
    public Response getAllClient() {
        List<Client> clients = clientRepository.findAll();
        logger.info("Client list successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Client list successfully found")
                .success(true)
                .data(clients.stream().map(clientMapper::toResponse).toList())
                .build();
    }

    @Override
    public Response updateClient(ClientRequest clientRequest, Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Client not found: " + clientId));
        client.setName(clientRequest.getName());
        client.setEmail(clientRequest.getEmail());
        client.setPhoneNumber(clientRequest.getPhoneNumber());
        clientRepository.save(client);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Client successfully updated")
                .success(true)
                .build();
    }
}
