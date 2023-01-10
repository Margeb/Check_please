package pl.margeb.check_please.bill.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.margeb.check_please.bill.domain.model.Bill;
import pl.margeb.check_please.bill.domain.model.BillOperation;
import pl.margeb.check_please.bill.service.BillOperationService;
import pl.margeb.check_please.bill.service.BillService;
import pl.margeb.check_please.common.dto.Message;
import pl.margeb.check_please.group.service.GroupService;
import pl.margeb.check_please.person.service.PersonService;

import java.util.HashSet;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/groups/{group-id}/bills/{bill-id}/operations")
public class BillOperationViewController {

    private final GroupService groupService;
    private final BillService billService;
    private final BillOperationService billOperationService;

    private final PersonService personService;

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
                      @Valid @ModelAttribute("billOperation") BillOperation billOperation,
                      BindingResult bindingResult,
                      RedirectAttributes ra,
                      Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("billOperation", billOperation);
            model.addAttribute("message", Message.error("Saving error"));
            model.addAttribute("group", groupService.getGroup(groupId));
            model.addAttribute("bill", billService.getBill(billId));
            model.addAttribute("people", personService.getAllPeople(groupId));
            return "bill_operation/add";
        }


        try{
            billOperationService.createBillOperation(billId, billOperation);
            ra.addFlashAttribute("message", Message.info("Bill operation created"));

        } catch (Exception e){
            model.addAttribute("billOperation", billOperation);
            model.addAttribute("message", Message.error("Unknown creating error"));
            model.addAttribute("group", groupService.getGroup(groupId));
            model.addAttribute("bill", billService.getBill(billId));
            model.addAttribute("people", personService.getAllPeople(groupId));
            return "bill_operation/add";
        }

        return "redirect:/groups/{group-id}/bills/{bill-id}";
    }
}
