package pl.margeb.check_please.group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.margeb.check_please.group.domain.model.Group;
import pl.margeb.check_please.group.service.GroupService;

import java.util.UUID;

@Controller
@RequestMapping("/admin/groups")
public class GroupAdminViewController {

    private final GroupService groupService;

    public GroupAdminViewController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public String indexView(Model model){
        model.addAttribute("groups", groupService.getGroups());
        return "admin/group/index";
    }

    @GetMapping("{id}")
    public String editView(Model model, @PathVariable UUID id){
        model.addAttribute("group", groupService.getGroup(id));
        return "admin/group/edit";
    }

    @PostMapping("{id}")
    public String edit(@ModelAttribute("group")Group group, @PathVariable UUID id){
        groupService.updateGroup(id,group);

        return "redirect:/admin/groups";
    }

    @GetMapping("{id}/delete")
    public String deleteView(@PathVariable UUID id){
        groupService.deleteGroup(id);
        return "redirect:/admin/groups";
    }

}
