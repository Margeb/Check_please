package pl.margeb.checkplease.bill.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pl.margeb.checkplease.CheckPleaseApplication;
import pl.margeb.checkplease.bill.domain.model.Bill;
import pl.margeb.checkplease.bill.domain.repository.BillRepository;
import pl.margeb.checkplease.group.domain.model.Group;
import pl.margeb.checkplease.group.service.GroupService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@ContextConfiguration(classes = CheckPleaseApplication.class)
class BillServiceIT {

    @Autowired
    private GroupService groupService;

    @Autowired
    private BillService billService;

    @Autowired
    private BillRepository billRepository;

    @BeforeEach
    void setUp() {
        groupService.deleteAllGroups();
    }

    @Test
    void shouldCreateBill() {
        //given
        Group group = groupService.createGroup(new Group("Group 1"));
        Bill bill = new Bill("Bill 1");

        //when
        Bill result = billService.createBill(group.getId(), bill);

        //then
        assertThat(result.getName()).isEqualTo(bill.getName());
        assertThat(result.getGroupId()).isEqualTo(group.getId());
    }

    @Test
    void shouldGetSingleBill() {
        //given
        Group group = groupService.createGroup(new Group("Group 1"));
        Bill bill = billService.createBill(group.getId(), new Bill("Bill 1"));

        //when
        Bill result = billService.getBill(bill.getId());

        //then
        assertThat(result.getName()).isEqualTo(bill.getName());
        assertThat(result.getId()).isEqualTo(bill.getId());
    }

    @Test
    void shouldGetAllBills() {
        //given
        Group group = groupService.createGroup(new Group("Group 1"));
        billRepository.saveAll(List.of(
                billService.createBill(group.getId(), new Bill("Bill 1")),
                billService.createBill(group.getId(), new Bill("Bill 2")),
                billService.createBill(group.getId(), new Bill("Bill 3"))
                ));

        //when
        List<Bill> bills = billService.getBills(group.getId());

        //then
        assertThat(bills)
                .hasSize(3)
                .extracting(Bill::getName)
                .containsExactlyInAnyOrder("Bill 1", "Bill 2", "Bill 3");
    }

    @Test
    void shouldUpdateSingleBill() {
        //given
        Group group = groupService.createGroup(new Group("Group 1"));
        Bill bill = billService.createBill(group.getId(), new Bill("Bill 1"));
        Bill billRequest = new Bill("Bill 2");

        //when
        Bill result = billService.updateBill(bill.getId(), billRequest);

        //then
        assertThat(result.getName()).isEqualTo(billRequest.getName());
        assertThat(result.getId()).isEqualTo(bill.getId());
    }

    @Test
    void shouldDeleteSingleBill() {
        //given
        Group group = groupService.createGroup(new Group("Group 1"));
        Bill bill = billService.createBill(group.getId(), new Bill("Bill 1"));

        //when
        billService.deleteBill(group.getId(), bill.getId());

        //then
        assertThat(bill.getId()).isNotIn(billService.getBills(group.getId()));
    }
}