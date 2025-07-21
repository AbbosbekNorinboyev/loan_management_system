package uz.pdp.loan_management_system.dto.request;

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
public class ClientRequest {
    @NotBlank(message = "name can not be null or empty")
    private String name;
    @NotBlank(message = "email can not be null or empty")
    private String email;
    @NotBlank(message = "phoneNumber can not be null or empty")
    private String phoneNumber;
    private Long authUserId;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}