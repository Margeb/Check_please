package pl.margeb.check_please.bill.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import pl.margeb.check_please.bill.domain.model.Bill;
import pl.margeb.check_please.bill.domain.model.BillOperation;
import pl.margeb.check_please.bill.domain.repository.BillOperationRepository;
import pl.margeb.check_please.bill.domain.repository.BillRepository;

import java.util.List;
import java.util.UUID;

@RestController
public class BillOperationService {

    private final BillOperationRepository billOperationRepository;

    private final BillRepository billRepository;

    public BillOperationService(BillOperationRepository billOperationRepository, BillRepository billRepository) {
        this.billOperationRepository = billOperationRepository;
        this.billRepository = billRepository;
    }

    @Transactional
    public BillOperation createBillOperation(UUID billId, BillOperation billOperationRequest) {
        BillOperation billOperation = new BillOperation();

        billOperation.setBill(billOperationRequest.getBill());
        billOperation.setPerson(billOperationRequest.getPerson());
        billOperation.setDeposit(billOperationRequest.getDeposit());
        billOperation.setCost(billOperationRequest.getCost());


        Bill bill = billRepository.getById(billId);

        bill.addBillOperation(billOperation);

        billRepository.save(bill);
        billOperationRepository.save(billOperation);

        return billOperation;
    }

    @Transactional(readOnly = true)
    public List<BillOperation> getBillOperations(UUID billId) {

        return billOperationRepository.findByBillId(billId);
    }

    @Transactional(readOnly = true)
    public BillOperation getBillOperation(UUID id) {

        return billOperationRepository.getById(id);
    }

    @Transactional
    public BillOperation updateBillOperation(UUID billOperationId, BillOperation billOperationRequest) {
        BillOperation billOperation = billOperationRepository.getById(billOperationId);

        billOperation.setDeposit(billOperationRequest.getDeposit());
        billOperation.setCost(billOperationRequest.getCost());

        return billOperationRepository.save(billOperation);
    }

    @Transactional
    public void deleteBillOperation(UUID billOperationId) {
        billOperationRepository.deleteById(billOperationId);
    }
}
