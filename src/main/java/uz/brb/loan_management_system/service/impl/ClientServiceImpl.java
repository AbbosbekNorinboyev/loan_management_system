package uz.brb.loan_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.request.ClientRequest;
import uz.brb.loan_management_system.entity.Client;
import uz.brb.loan_management_system.exception.ResourceNotFoundException;
import uz.brb.loan_management_system.mapper.ClientMapper;
import uz.brb.loan_management_system.repository.ClientRepository;
import uz.brb.loan_management_system.service.ClientService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.brb.loan_management_system.util.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
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
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response getClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found: " + clientId));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Client successfully found")
                .success(true)
                .data(clientMapper.toResponse(client))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
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
                .data(clientMapper.dtoList(clients))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response updateClient(ClientRequest clientRequest, Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found: " + clientId));
        clientMapper.update(client, clientRequest);
        client.setUpdatedAt(LocalDateTime.now());
        clientRepository.save(client);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Client successfully updated")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response getClientByPhoneNumber(String phoneNumber) {
        Client client = clientRepository.findByPhoneNumber(phoneNumber);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Client successfully found by phone number")
                .success(true)
                .data(clientMapper.toResponse(client))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
