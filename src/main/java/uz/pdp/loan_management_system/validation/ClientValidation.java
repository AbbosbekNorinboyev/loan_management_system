package uz.pdp.loan_management_system.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.loan_management_system.dto.ErrorDTO;
import uz.pdp.loan_management_system.request.ClientRequest;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientValidation {
    public List<ErrorDTO> validate(ClientRequest clientRequest) {
        List<ErrorDTO> errors = new ArrayList<>();
        // Email validatsiyasi
        if (!clientRequest.getEmail().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            errors.add(new ErrorDTO("email", "email is invalid"));
        }
        // Telefon raqam validatsiyasi (foydalanishga moslangan)
        if (!clientRequest.getPhoneNumber().matches("^\\+9989\\d{8}$")) {
            errors.add(new ErrorDTO("phoneNumber", "phoneNumber is invalid"));
        }
        return errors;
    }
}
