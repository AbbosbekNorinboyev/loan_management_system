package uz.pdp.loan_management_system.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClientResponse {
    private String name;
    private String email;
    private String phoneNumber;
    private Long authUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}