package uz.pdp.loan_management_system.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.loan_management_system.enums.Role;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
