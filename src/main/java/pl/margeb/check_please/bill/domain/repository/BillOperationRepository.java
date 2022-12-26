package pl.margeb.check_please.bill.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.margeb.check_please.bill.domain.model.Bill;
import pl.margeb.check_please.bill.domain.model.BillOperation;

import java.util.List;
import java.util.UUID;

@Repository
public interface BillOperationRepository extends JpaRepository<BillOperation, UUID> {
    List<BillOperation> findByBillId(UUID billId);
}
