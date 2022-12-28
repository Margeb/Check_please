package pl.margeb.check_please.bill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.margeb.check_please.bill.domain.model.Bill;
import pl.margeb.check_please.bill.service.BillOperationService;
import pl.margeb.check_please.bill.service.BillService;
import pl.margeb.check_please.group.domain.model.Group;
import pl.margeb.check_please.group.service.GroupService;
import pl.margeb.check_please.person.service.PersonService;

import java.util.UUID;

@Controller
@RequestMapping("/groups/{group-id}/bills")
public class BillViewController {

    private final GroupService groupService;
    private final BillService billService;
    private final BillOperationService billOperationService;

    private final PersonService personService;

    public BillViewController(GroupService groupService,
                              BillService billService,
                              BillOperationService billOperationService,
                              PersonService personService) {
        this.groupService = groupService;
        this.billService = billService;
        this.billOperationService = billOperationService;
        this.personService = personService;
    }

    @GetMapping
    public String indexView(Model model, @PathVariable("group-id")UUID groupId){
        model.addAttribute("groups", groupService.getGroups());

        return "group/index";
    }

    @GetMapping("{id}")
    public String singleView(Model model, @PathVariable UUID id, @PathVariable("group-id") UUID groupId){
        model.addAttribute("bill", billService.getBill(id));
        model.addAttribute("billOperations", billOperationService.getBillOperations(id));

        return "bill/single";
    }

    @GetMapping("add")
    public String addView(Model model, @PathVariable("group-id") UUID groupId){
        model.addAttribute("bill", new Bill());
        model.addAttribute("group", groupService.getGroup(groupId));
        return "bill/add";
    }

    @PostMapping
    public String add(@PathVariable("group-id") UUID groupId, Bill bill){
        billService.createBill(groupId, bill);

        return "redirect:/groups/{group-id}";
    }
}
