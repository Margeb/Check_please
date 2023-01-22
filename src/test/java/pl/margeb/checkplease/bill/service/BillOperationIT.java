package pl.margeb.checkplease.bill.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import pl.margeb.checkplease.CheckPleaseApplication;
import pl.margeb.checkplease.bill.domain.model.Bill;
import pl.margeb.checkplease.bill.domain.model.BillOperation;
import pl.margeb.checkplease.group.domain.model.Group;
import pl.margeb.checkplease.group.service.GroupService;
import pl.margeb.checkplease.person.domain.model.Person;
import pl.margeb.checkplease.person.domain.repository.PersonRepository;
import pl.margeb.checkplease.person.service.PersonService;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = CheckPleaseApplication.class)
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
        BillOperation billOperation = new BillOperation(bill.getId(), person.getId(), BigDecimal.ZERO, BigDecimal.TEN);

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
            BillOperation billOperation = billOperationService.createBillOperation(bill.getId(), new BillOperation(bill.getId(), person.getId(), BigDecimal.ZERO, BigDecimal.TEN));

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
                BillOperation billOperationRequest = new BillOperation(bill.getId(), person.getId(), BigDecimal.ONE, BigDecimal.TEN);
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
        BillOperation billOperation = billOperationService.createBillOperation(bill.getId(), new BillOperation(bill.getId(), person.getId(), BigDecimal.ZERO, BigDecimal.TEN));
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
        BillOperation billOperation = billOperationService.createBillOperation(bill.getId(), new BillOperation(bill.getId(), person.getId(), BigDecimal.ZERO, BigDecimal.TEN));

        //when

        billOperationService.deleteBillOperation(group.getId(), billOperation.getId());

        //then

        assertThat(billOperation).isNotIn(billOperationService.getBillOperations(bill.getId()));

    }
}