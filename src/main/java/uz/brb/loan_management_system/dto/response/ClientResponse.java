package uz.brb.loan_management_system.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClientResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Long authUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}