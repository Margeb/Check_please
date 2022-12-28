package pl.margeb.check_please.person.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.margeb.check_please.group.service.GroupService;
import pl.margeb.check_please.person.domain.model.Person;
import pl.margeb.check_please.person.service.PersonService;

import java.util.UUID;

@Controller
@RequestMapping("/groups/{group-id}/people")
public class PersonViewController {

    private final GroupService groupService;

    private final PersonService personService;

    public PersonViewController(GroupService groupService, PersonService personService) {
        this.groupService = groupService;
        this.personService = personService;
    }

    @GetMapping
    public String indexView(Model model, @PathVariable("group-id")UUID groupId){
        model.addAttribute("groups", groupService.getGroups());

        return "group/index";
    }

    @GetMapping("add")
    public String addView(Model model, @PathVariable("group-id") UUID groupId){
        model.addAttribute("person", new Person());
        model.addAttribute("group", groupService.getGroup(groupId));
        return "person/add";
    }

    @PostMapping
    public String add(@PathVariable("group-id") UUID groupId, Person person){
        personService.createPerson(groupId, person);

        return "redirect:/groups/{group-id}";
    }
}
