package uz.brb.loan_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.brb.loan_management_system.entity.Account;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT a.account_type, " +
            "COUNT(*) AS totalCount, " +
            "SUM(a.balance) AS totalBalance " +
            "FROM account a " +
            "WHERE (?1 is null or a.account_type = ?1 )" +
            "GROUP BY a.account_type",
            nativeQuery = true)
    List<Object[]> findAccountByAccountType(String accountType);
}