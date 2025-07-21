package uz.pdp.loan_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.loan_management_system.dto.ErrorDTO;
import uz.pdp.loan_management_system.dto.ResponseDTO;
import uz.pdp.loan_management_system.entity.Client;
import uz.pdp.loan_management_system.exception.ResourceNotFoundException;
import uz.pdp.loan_management_system.mapper.ClientMapper;
import uz.pdp.loan_management_system.mapper.interfaces.ClientMapperInterface;
import uz.pdp.loan_management_system.repository.ClientRepository;
import uz.pdp.loan_management_system.dto.request.ClientRequest;
import uz.pdp.loan_management_system.dto.response.ClientResponse;
import uz.pdp.loan_management_system.service.ClientService;
import uz.pdp.loan_management_system.validation.ClientValidation;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientValidation clientValidation;
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ClientMapperInterface clientMapperInterface;
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Override
    public ResponseDTO<ClientResponse> createClient(ClientRequest clientRequest) {
        List<ErrorDTO> errors = clientValidation.validate(clientRequest);
        if (!errors.isEmpty()) {
            logger.error("Validation error createClient");
            return ResponseDTO.<ClientResponse>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Validation error")
                    .success(false)
                    .build();
        }
        Client client = clientMapper.toEntity(clientRequest);
        clientRepository.save(client);
        logger.info("Client successfully saved");
        return ResponseDTO.<ClientResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Client successfully saved")
                .success(true)
                .data(clientMapper.toResponse(client))
                .build();
    }

    @Override
    public ResponseDTO<ClientResponse> getClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found: " + clientId));
        return ResponseDTO.<ClientResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Client successfully found")
                .success(true)
                .data(clientMapperInterface.toClientResponse(client))
                .build();
    }

    @Override
    public ResponseDTO<List<ClientResponse>> getAllClient() {
        List<Client> clients = clientRepository.findAll();
        logger.info("Client list successfully found");
        return ResponseDTO.<List<ClientResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Client list successfully found")
                .success(true)
                .data(clients.stream().map(clientMapper::toResponse).toList())
                .build();
    }

    @Override
    public ResponseDTO<Void> updateClient(ClientRequest clientRequest, Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found: " + clientId));
        client.setName(clientRequest.getName());
        client.setEmail(clientRequest.getEmail());
        client.setPhoneNumber(clientRequest.getPhoneNumber());
        clientRepository.save(client);
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Client successfully updated")
                .success(true)
                .build();
    }
}
