package uz.brb.loan_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.brb.loan_management_system.dto.request.ClientRequest;
import uz.brb.loan_management_system.dto.response.ClientResponse;
import uz.brb.loan_management_system.entity.Client;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientMapper {
    public Client toEntity(ClientRequest clientRequest) {
        return Client.builder()
                .name(clientRequest.getName())
                .email(clientRequest.getEmail())
                .phoneNumber(clientRequest.getPhoneNumber())
                .createdAt(clientRequest.getCreatedAt())
                .updatedAt(clientRequest.getUpdatedAt())
                .build();
    }

    public ClientResponse toResponse(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .authUserId(client.getAuthUser().getId())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }

    public List<ClientResponse> dtoList(List<Client> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(Client entity, ClientRequest clientRequest) {
        if (clientRequest == null) {
            return;
        }
        if (clientRequest.getName() != null && !clientRequest.getName().trim().isEmpty()) {
            entity.setName(clientRequest.getName());
        }
        if (clientRequest.getEmail() != null && !clientRequest.getEmail().trim().isEmpty()) {
            entity.setEmail(clientRequest.getEmail());
        }
        if (clientRequest.getPhoneNumber() != null && !clientRequest.getPhoneNumber().trim().isEmpty()) {
            entity.setPhoneNumber(clientRequest.getPhoneNumber());
        }
    }
}
