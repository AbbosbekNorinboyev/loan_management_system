package uz.brb.loan_management_system.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import uz.brb.loan_management_system.enums.AccountType;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double balance;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @CreationTimestamp // automatitskiy yaratilingandagi vaqtni qo'yib beradi
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "auth_user_id")
    private AuthUser authUser;
}
