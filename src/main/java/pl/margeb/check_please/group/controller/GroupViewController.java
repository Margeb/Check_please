package pl.margeb.check_please.group.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.margeb.check_please.bill.service.BillService;
import pl.margeb.check_please.common.dto.Message;
import pl.margeb.check_please.group.domain.model.Group;
import pl.margeb.check_please.group.service.GroupService;
import pl.margeb.check_please.person.service.PersonService;

import java.util.UUID;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/groups")
public class GroupViewController {

    private final GroupService groupService;
    private final BillService billService;
    private final PersonService personService;


    @GetMapping
    public String indexView(Model model){
        model.addAttribute("groups", groupService.getGroups(Pageable.unpaged()));

        return "group/index";
    }

    @GetMapping("{id}")
    public String singleView(Model model, @PathVariable UUID id){
        model.addAttribute("group", groupService.getGroup(id));
        model.addAttribute("bills", billService.getBills(id));
        model.addAttribute("people", personService.getAllPeople(id));

        return "group/single";
    }

    @GetMapping("add")
    public String addView(Model model){
        model.addAttribute("group", new Group());
        return "group/add";
    }

    @PostMapping
    public String add(@Valid @ModelAttribute("group") Group group,
                      BindingResult bindingResult,
                      RedirectAttributes ra,
                      Model model){

        if(bindingResult.hasErrors()){
            log.error("Error while creating Group: " + bindingResult.getAllErrors());

            model.addAttribute("group", group);
            model.addAttribute("message", Message.error("Saving error"));
            return "group/add";
        }

        try{
            groupService.createGroup(group);
            ra.addFlashAttribute("message", Message.info("Group created"));
            log.info("Group created: " + group.getName());

        } catch (Exception e){
            log.error("Unknown error while creating Group:" + e);

            model.addAttribute("group", group);
            model.addAttribute("message", Message.error("Unknown creating error"));
            return "group/add";
        }



        return "redirect:/groups";
    }
}
