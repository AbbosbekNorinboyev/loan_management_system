package uz.pdp.loan_management_system.mapper.interfaces;

import org.mapstruct.Mapper;
import uz.pdp.loan_management_system.entity.Client;
import uz.pdp.loan_management_system.request.ClientRequest;
import uz.pdp.loan_management_system.response.ClientResponse;

@Mapper(componentModel = "spring")
public interface ClientMapperInterface {
    Client toClientEntity(ClientRequest clientRequest);
    ClientResponse toClientResponse(Client client);
}
