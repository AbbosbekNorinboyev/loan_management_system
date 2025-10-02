package uz.brb.loan_management_system.repository;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.brb.loan_management_system.entity.AuthUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);

    @Query("select count(*), a.role from AuthUser a group by a.role")
    List<Tuple> roleStatistics();
}