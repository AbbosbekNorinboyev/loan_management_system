package uz.brb.loan_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RegisterDto {
    @NotBlank(message = "username can not be null or empty")
    private String username;
    @NotBlank(message = "password can not be null or empty")
    private String password;
}
