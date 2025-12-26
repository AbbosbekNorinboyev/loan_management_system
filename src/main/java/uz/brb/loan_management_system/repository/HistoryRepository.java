package uz.brb.loan_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.brb.loan_management_system.entity.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
}