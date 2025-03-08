package uz.pdp.loan_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.loan_management_system.entity.AuthUser;
import uz.pdp.loan_management_system.entity.Client;
import uz.pdp.loan_management_system.exception.ResourceNotFoundException;
import uz.pdp.loan_management_system.repository.AuthUserRepository;
import uz.pdp.loan_management_system.request.ClientRequest;
import uz.pdp.loan_management_system.response.ClientResponse;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final AuthUserRepository authUserRepository;

    public Client toEntity(ClientRequest clientRequest) {
        AuthUser authUser = authUserRepository.findById(clientRequest.getAuthUserId())
                .orElseThrow(() -> new ResourceNotFoundException("AuthUser not found: " + clientRequest.getAuthUserId()));
        return Client.builder()
                .name(clientRequest.getName())
                .email(clientRequest.getEmail())
                .phoneNumber(clientRequest.getPhoneNumber())
                .authUser(authUser)
                .createdAt(clientRequest.getCreatedAt())
                .updatedAt(clientRequest.getUpdatedAt())
                .build();
    }

    public ClientResponse toResponse(Client client) {
        return ClientResponse.builder()
                .name(client.getName())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .authUserId(client.getAuthUser().getId())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }
}
