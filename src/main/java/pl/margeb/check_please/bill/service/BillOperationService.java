package pl.margeb.check_please.bill.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import pl.margeb.check_please.bill.domain.model.Bill;
import pl.margeb.check_please.bill.domain.model.BillOperation;
import pl.margeb.check_please.bill.domain.repository.BillOperationRepository;
import pl.margeb.check_please.bill.domain.repository.BillRepository;
import pl.margeb.check_please.person.domain.model.Person;
import pl.margeb.check_please.person.domain.repository.PersonRepository;
import pl.margeb.check_please.person.service.PersonService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class BillOperationService {

    private final BillOperationRepository billOperationRepository;
    private final BillRepository billRepository;
    private final PersonRepository personRepository;
    private final PersonService personService;


    @Transactional
    public BillOperation createBillOperation(UUID billId, BillOperation billOperationRequest) {

        BillOperation billOperation = new BillOperation();
        Bill bill = billRepository.getById(billId);

        billOperation.setBill(bill);
        billOperation.setPersonId(billOperationRequest.getPersonId());
        billOperation.setDeposit(billOperationRequest.getDeposit());
        billOperation.setCost(billOperationRequest.getCost());

        Person person = personService.getPerson(billOperation.getPersonId());

        bill.addBillOperation(billOperation);
        person.addBillOperation(billOperation);
        person.setBalance(person.getBalance().add(billOperation.getDeposit()).add(billOperation.getCost().negate()));

        billOperationRepository.save(billOperation);
        personRepository.save(person);
        billRepository.save(bill);


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
