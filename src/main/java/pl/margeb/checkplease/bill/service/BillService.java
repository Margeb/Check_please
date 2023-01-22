package pl.margeb.checkplease.bill.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.margeb.checkplease.bill.domain.model.Bill;
import pl.margeb.checkplease.bill.domain.model.BillOperation;
import pl.margeb.checkplease.bill.domain.repository.BillRepository;
import pl.margeb.checkplease.group.domain.model.Group;
import pl.margeb.checkplease.group.domain.repository.GroupRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final GroupRepository groupRepository;
    private final BillOperationService billOperationService;


    @Transactional
    public Bill createBill(UUID groupId, Bill billRequest) {
        Bill bill = new Bill();
        bill.setName(billRequest.getName());

        Group group = groupRepository.getById(groupId);

        bill.setGroupId(group.getId());

        group.addBill(bill);

        billRepository.save(bill);
        groupRepository.save(group);

        return bill;
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
    public void deleteBill(UUID groupId, UUID billId) {
        for(BillOperation billOperation : billOperationService.getBillOperations(billId)){
            billOperationService.deleteBillOperation(groupId, billOperation.getId());
        }
        Bill bill = billRepository.getById(billId);
        Group group = groupRepository.getById(groupId);
        group.deleteBill(bill);
        billRepository.deleteById(billId);
    }
}
