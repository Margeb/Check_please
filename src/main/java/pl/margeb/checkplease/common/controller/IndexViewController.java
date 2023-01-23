package pl.margeb.checkplease.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.margeb.checkplease.group.service.GroupService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class IndexViewController {

    private final GroupService groupService;

    @GetMapping
    public String indexView(Model model){
        model.addAttribute("groups", groupService.getGroups(Pageable.unpaged()));

        return "redirect:/groups";
    }
}
