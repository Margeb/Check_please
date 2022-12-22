package pl.margeb.check_please.group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.margeb.check_please.bill.service.BillService;
import pl.margeb.check_please.group.domain.model.Group;
import pl.margeb.check_please.group.service.GroupService;
import pl.margeb.check_please.person.service.PersonService;

import java.util.UUID;

@Controller
@RequestMapping("/groups")
public class GroupViewController {

    private final GroupService groupService;
    private final BillService billService;

    private final PersonService personService;

    public GroupViewController(GroupService groupService, BillService billService, PersonService personService) {
        this.groupService = groupService;
        this.billService = billService;
        this.personService = personService;
    }

    @GetMapping
    public String indexView(Model model){
        model.addAttribute("groups", groupService.getGroups());

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
    public String add(Group group){
        groupService.createGroup(group);

        return "redirect:/groups";
    }
}
