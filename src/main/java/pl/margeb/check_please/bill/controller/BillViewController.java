package pl.margeb.check_please.bill.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.margeb.check_please.bill.domain.model.Bill;
import pl.margeb.check_please.bill.service.BillOperationService;
import pl.margeb.check_please.bill.service.BillService;
import pl.margeb.check_please.common.dto.Message;
import pl.margeb.check_please.group.domain.model.Group;
import pl.margeb.check_please.group.service.GroupService;
import pl.margeb.check_please.person.service.PersonService;

import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/groups/{group-id}/bills")
public class BillViewController {

    private final GroupService groupService;
    private final BillService billService;
    private final BillOperationService billOperationService;
    private final PersonService personService;


    @GetMapping
    public String indexView(Model model, @PathVariable("group-id")UUID groupId){
        model.addAttribute("groups", groupService.getGroups(Pageable.unpaged()));

        return "group/index";
    }

    @GetMapping("{id}")
    public String singleView(Model model, @PathVariable UUID id, @PathVariable("group-id") UUID groupId){
        model.addAttribute("bill", billService.getBill(id));
        model.addAttribute("billOperations", billOperationService.getBillOperations(id));
        model.addAttribute("groupId", groupId);

        return "bill/single";
    }

    @GetMapping("add")
    public String addView(Model model, @PathVariable("group-id") UUID groupId){
        model.addAttribute("bill", new Bill());
        model.addAttribute("group", groupService.getGroup(groupId));
        return "bill/add";
    }

    @PostMapping
    public String add(@PathVariable("group-id") UUID groupId,
                      @Valid @ModelAttribute("bill") Bill bill,
                      BindingResult bindingResult,
                      RedirectAttributes ra,
                      Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("bill", bill);
            model.addAttribute("message", Message.error("Saving error"));
            model.addAttribute("group", groupService.getGroup(groupId));
            return "bill/add";
        }

        try{
            billService.createBill(groupId, bill);
            ra.addFlashAttribute("message", Message.info("Bill created"));

        } catch (Exception e){
            model.addAttribute("bill", bill);
            model.addAttribute("message", Message.error("Unknown creating error"));
            model.addAttribute("group", groupService.getGroup(groupId));
            return "bill/add";
        }



        return "redirect:/groups/{group-id}";
    }
}
