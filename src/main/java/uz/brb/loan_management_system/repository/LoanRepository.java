package uz.brb.loan_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.brb.loan_management_system.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}