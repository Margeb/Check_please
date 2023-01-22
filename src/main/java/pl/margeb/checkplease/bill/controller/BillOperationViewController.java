package pl.margeb.checkplease.bill.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.margeb.checkplease.bill.domain.model.BillOperation;
import pl.margeb.checkplease.bill.service.BillOperationService;
import pl.margeb.checkplease.bill.service.BillService;
import pl.margeb.checkplease.common.dto.Message;
import pl.margeb.checkplease.group.service.GroupService;
import pl.margeb.checkplease.person.service.PersonService;

import java.util.UUID;

@Controller
@AllArgsConstructor
@Slf4j
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
            log.error("Error adding BillOperation: " + bindingResult.getAllErrors());

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

            log.info("BillOperation created for Bill: " + billService.getBill(billId).getName() + " ,Person: " + personService.getPerson(billOperation.getPersonId()).getName());

        } catch (Exception e){
            log.error("Unknown error while creating BillOperation: " + e);

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
