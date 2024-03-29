package pl.margeb.checkplease.bill.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.margeb.checkplease.bill.domain.model.BillOperation;

import java.util.List;
import java.util.UUID;

@Repository
public interface BillOperationRepository extends JpaRepository<BillOperation, UUID> {
    List<BillOperation> findByBillId(UUID billId);
}
