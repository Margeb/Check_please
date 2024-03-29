package pl.margeb.checkplease.group.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.margeb.checkplease.common.dto.Message;
import pl.margeb.checkplease.group.domain.model.Group;
import pl.margeb.checkplease.group.service.GroupService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/admin/groups")
public class GroupAdminViewController {

    private final GroupService groupService;

    @GetMapping
    public String indexView(
                            @RequestParam(name = "s", required = false) String search,
                            @RequestParam(name = "field", required = false, defaultValue = "id") String field,
                            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
                            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
                            Model model
                            ){

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), field);

        String reverseSort = null;
        if("asc".equals(direction)){
            reverseSort = "desc";
        } else{
            reverseSort = "asc";
        }

        Page<Group> groupsPage = groupService.getGroups(search, pageable);
        model.addAttribute("groupsPage", groupsPage);
        model.addAttribute("search", search);
        model.addAttribute("reverseSort", reverseSort);
        paging(model, groupsPage);

        return "admin/group/index";
    }

    @GetMapping("{id}")
    public String editView(Model model, @PathVariable UUID id){
        model.addAttribute("group", groupService.getGroup(id));

        return "admin/group/edit";
    }

    @PostMapping("{id}")
    public String edit(
                        @PathVariable UUID id,
                        @Valid @ModelAttribute("group")Group group,
                        BindingResult bindingResult,
                        RedirectAttributes ra,
                        Model model
                        ){

        if(bindingResult.hasErrors()){
            log.error("Error while editing Group:" + bindingResult.getAllErrors());
            model.addAttribute("group", group);
            model.addAttribute("message", Message.error("Saving error"));
            return "admin/group/edit";
        }

        try {
            groupService.updateGroup(id,group);
            ra.addFlashAttribute("message", Message.info("Group saved"));
            log.info("Group saved: " + group.getName());

        } catch(Exception e){
            log.error("Unknown error while editing Group:" + e);
            model.addAttribute("group", groupService.getGroup(id));
            model.addAttribute("message", Message.error("Unknown saving error"));
            return "admin/group/edit";
        }


        return "redirect:/admin/groups";
    }

    @GetMapping("{id}/delete")
    public String deleteView(@PathVariable UUID id, RedirectAttributes ra){
        try{
            groupService.deleteGroup(id);
            ra.addFlashAttribute("message", Message.info("Group deleted"));
            log.info("Group deleted");

        } catch (Exception e){
            log.error("Unknown error while deleting Group: " + e);
            ra.addFlashAttribute("message", Message.error("Unknown error while deleting Group"));
            return "redirect:/admin/groups";
        }


        return "redirect:/admin/groups";
    }

    private void paging(Model model, Page page){
        int TotalPages = page.getTotalPages();
        if (TotalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, TotalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }

}
