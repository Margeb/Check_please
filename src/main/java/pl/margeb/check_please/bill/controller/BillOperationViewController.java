package pl.margeb.check_please.bill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.margeb.check_please.bill.domain.model.Bill;
import pl.margeb.check_please.bill.domain.model.BillOperation;
import pl.margeb.check_please.bill.service.BillOperationService;
import pl.margeb.check_please.bill.service.BillService;
import pl.margeb.check_please.group.service.GroupService;
import pl.margeb.check_please.person.service.PersonService;

import java.util.HashSet;
import java.util.UUID;

@Controller
@RequestMapping("/groups/{group-id}/bills/{bill-id}/operations")
public class BillOperationViewController {

    private final GroupService groupService;
    private final BillService billService;
    private final BillOperationService billOperationService;

    private final PersonService personService;

    public BillOperationViewController(GroupService groupService,
                                       BillService billService,
                                       BillOperationService billOperationService,
                                       PersonService personService) {
        this.groupService = groupService;
        this.billService = billService;
        this.billOperationService = billOperationService;
        this.personService = personService;
    }

    @GetMapping("add")
    public String addView(Model model,
                          @PathVariable("bill-id") UUID billId,
                          @PathVariable("group-id") UUID groupId){

        model.addAttribute("billOperation", new BillOperation());
        model.addAttribute("bill", billService.getBill(billId));
        model.addAttribute("people", personService.getAllPeople(groupId));
        model.addAttribute("group", groupService.getGroup(groupId));

        return "bill_operation/add";
    }

    @PostMapping
    public String add(@PathVariable("group-id") UUID groupId,
                      @PathVariable("bill-id") UUID billId,
                      BillOperation billOperation){
        billOperationService.createBillOperation(billId, billOperation);

        return "redirect:/groups/{group-id}/bills/{bill-id}";
    }
}
