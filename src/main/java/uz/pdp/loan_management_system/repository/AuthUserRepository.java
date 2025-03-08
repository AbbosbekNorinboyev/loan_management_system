package uz.pdp.loan_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.loan_management_system.entity.AuthUser;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);
}