package pl.margeb.check_please.bill.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.margeb.check_please.bill.domain.model.Bill;
import pl.margeb.check_please.bill.domain.model.BillOperation;
import pl.margeb.check_please.group.domain.model.Group;
import pl.margeb.check_please.group.service.GroupService;
import pl.margeb.check_please.person.domain.model.Person;
import pl.margeb.check_please.person.domain.repository.PersonRepository;
import pl.margeb.check_please.person.service.PersonService;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BillOperationIT {

    @Autowired
    private GroupService groupService;
    @Autowired
    private BillOperationService billOperationService;
    @Autowired
    private BillService billService;
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;



    @BeforeEach
    void setUp() {
        groupService.deleteAllGroups();
    }

    @Test
    void shouldCreateBillOperation() {

        //given

        Group group = groupService.createGroup(new Group("Group 1"));
        Bill bill = billService.createBill(group.getId(), new Bill("Bill 1"));
        Person person = personService.createPerson(group.getId(), new Person("Person 1"));
        BillOperation billOperation = new BillOperation(bill, person.getId(), BigDecimal.ZERO, BigDecimal.TEN);

        //when

        BillOperation result = billOperationService.createBillOperation(bill.getId(), billOperation);

        //then

       assertThat(result.getId()).isEqualTo(billOperation.getId());
    }

    @Test
    void shouldGetSingleBillOperation() {

        //given

            Group group = groupService.createGroup(new Group("Group 1"));
            Bill bill = billService.createBill(group.getId(), new Bill("Bill 1"));
            Person person = personService.createPerson(group.getId(), new Person("Person 1"));
            BillOperation billOperation = billOperationService.createBillOperation(bill.getId(), new BillOperation(bill, person.getId(), BigDecimal.ZERO, BigDecimal.TEN));

        //when

            BillOperation result = billOperationService.getBillOperation(billOperation.getId());

        //then

            assertThat(result.getId()).isEqualTo(billOperation.getId());

    }

    @Test
    void shouldGetAllBillOperations() {

        //given

            Group group = groupService.createGroup(new Group("Group 1"));
            Bill bill = billService.createBill(group.getId(), new Bill("Bill 1"));
            List<Person> people = personRepository.saveAll(List.of(
                    personService.createPerson(group.getId(), new Person("Person 1")),
                    personService.createPerson(group.getId(), new Person("Person 2")),
                    personService.createPerson(group.getId(), new Person("Person 3"))
            ));

            for(Person person : people){
                BillOperation billOperationRequest = new BillOperation(bill, person.getId(), BigDecimal.ONE, BigDecimal.TEN);
                billOperationService.createBillOperation(bill.getId(), billOperationRequest);
            }


        //when

            List<BillOperation> result = billOperationService.getBillOperations(bill.getId());

        //then

            assertThat(result).hasSize(3);

    }


    @Test
    void shouldUpdateSingleBillOperation() {

        //given

        Group group = groupService.createGroup(new Group("Group 1"));
        Bill bill = billService.createBill(group.getId(), new Bill("Bill 1"));
        Person person = personService.createPerson(group.getId(), new Person("Person 1"));
        BillOperation billOperation = billOperationService.createBillOperation(bill.getId(), new BillOperation(bill, person.getId(), BigDecimal.ZERO, BigDecimal.TEN));
        BillOperation billOperationRequest = new BillOperation();
        billOperationRequest.setDeposit(BigDecimal.TEN);
        billOperationRequest.setCost(BigDecimal.TWO);

        //when

        BillOperation result = billOperationService.updateBillOperation(billOperation.getId(), billOperationRequest);

        //then

        assertThat(result.getId()).isEqualTo(billOperation.getId());
        assertThat(result.getDeposit()).isEqualTo(billOperationRequest.getDeposit());
        assertThat(result.getCost()).isEqualTo(billOperationRequest.getCost());
    }

    @Test
    void shouldDeleteSingleBillOperation() {

        //given

        Group group = groupService.createGroup(new Group("Group 1"));
        Bill bill = billService.createBill(group.getId(), new Bill("Bill 1"));
        Person person = personService.createPerson(group.getId(), new Person("Person 1"));
        BillOperation billOperation = billOperationService.createBillOperation(bill.getId(), new BillOperation(bill, person.getId(), BigDecimal.ZERO, BigDecimal.TEN));

        //when

        billOperationService.deleteBillOperation(billOperation.getId());

        //then

        assertThat(billOperation).isNotIn(billOperationService.getBillOperations(bill.getId()));

    }
}