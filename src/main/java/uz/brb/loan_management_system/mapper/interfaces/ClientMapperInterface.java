package uz.brb.loan_management_system.mapper.interfaces;

import org.mapstruct.Mapper;
import uz.brb.loan_management_system.dto.request.ClientRequest;
import uz.brb.loan_management_system.dto.response.ClientResponse;
import uz.brb.loan_management_system.entity.Client;

@Mapper(componentModel = "spring")
public interface ClientMapperInterface {
    Client toClientEntity(ClientRequest clientRequest);

    ClientResponse toClientResponse(Client client);
}
