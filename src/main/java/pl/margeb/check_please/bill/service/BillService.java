package pl.margeb.check_please.bill.service;

import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import pl.margeb.check_please.bill.domain.model.Bill;
import pl.margeb.check_please.bill.domain.repository.BillRepository;
import pl.margeb.check_please.group.domain.model.Group;
import pl.margeb.check_please.group.domain.repository.GroupRepository;

import java.util.List;
import java.util.UUID;

@RestController
public class BillService {

    private final BillRepository billRepository;

    private final GroupRepository groupRepository;

    public BillService(BillRepository billRepository, GroupRepository groupRepository) {
        this.billRepository = billRepository;
        this.groupRepository = groupRepository;
    }

    @Transactional
    public Bill createBill(UUID groupId, Bill billRequest) {
        Bill bill = new Bill();
        bill.setName(billRequest.getName());

        Group group = groupRepository.getById(groupId);

        bill.setGroup(group);

        group.addBill(bill);

        groupRepository.save(group);

        return billRepository.save(bill);
    }

    @Transactional(readOnly = true)
    public List<Bill> getBills(UUID groupId) {

        return billRepository.findByGroupId(groupId);
    }

    @Transactional(readOnly = true)
    public Bill getBill(UUID id) {

        return billRepository.getById(id);
    }

    @Transactional
    public Bill updateBill(UUID billId, Bill billRequest) {
        Bill bill = billRepository.getById(billId);
        bill.setName(billRequest.getName());

        return billRepository.save(bill);
    }

    @Transactional
    public void deleteBill(UUID billId) {
        billRepository.deleteById(billId);
    }
}
