package pl.margeb.check_please.person.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.margeb.check_please.common.dto.Message;
import pl.margeb.check_please.group.service.GroupService;
import pl.margeb.check_please.person.domain.model.Person;
import pl.margeb.check_please.person.service.PersonService;

import java.util.UUID;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/groups/{group-id}/people")
public class PersonViewController {

    private final GroupService groupService;
    private final PersonService personService;



    @GetMapping
    public String indexView(Model model, @PathVariable("group-id")UUID groupId){
        model.addAttribute("groups", groupService.getGroups(Pageable.unpaged()));

        return "group/index";
    }

    @GetMapping("add")
    public String addView(Model model, @PathVariable("group-id") UUID groupId){
        model.addAttribute("person", new Person());
        model.addAttribute("group", groupService.getGroup(groupId));
        return "person/add";
    }

    @PostMapping
    public String add(@PathVariable("group-id") UUID groupId,
                      @Valid @ModelAttribute("person") Person person,
                      BindingResult bindingResult,
                      RedirectAttributes ra,
                      Model model){

        if(bindingResult.hasErrors()){
            log.error("Error saving Person: " + bindingResult.getAllErrors());

            model.addAttribute("person", person);
            model.addAttribute("message", Message.error("Saving error"));
            model.addAttribute("group", groupService.getGroup(groupId));
            return "person/add";
        }

        try{
            personService.createPerson(groupId, person);
            ra.addFlashAttribute("message", Message.info("Person created"));
            log.info("Person created: " + person.getName());

        } catch (Exception e){
            log.error("Unknown error while creating Person: " + e);

            model.addAttribute("person", person);
            model.addAttribute("message", Message.error("Unknown creating error"));
            model.addAttribute("group", groupService.getGroup(groupId));
            return "person/add";
        }



        return "redirect:/groups/{group-id}";
    }
}
