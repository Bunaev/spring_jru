package jru_apl.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jru_apl.domain.Status;
import jru_apl.dto.TaskDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jru_apl.service.TaskService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final int DEFAULT_PAGE_NUMBER = 0;


    @GetMapping("/")
    public String getAllTasks(
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER + "") int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE + "") int size,
            Model model) {

        model.addAttribute("tasks", taskService.getAllTasks(page, size));
        model.addAttribute("statusValues", Status.values());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        if (!model.containsAttribute("taskDTO")) {
            model.addAttribute("taskDTO", TaskDTO.builder().build());
        }
        return "index";
    }

    @PostMapping("/update-status")
    public String updateStatus(
            @RequestParam Long taskId,
            @RequestParam Status status) {
        taskService.updateStatus(taskId, status);
        return "redirect:/";
    }
    @PostMapping("/delete-task")
    public String deleteTask(@RequestParam(name = "id") Long id) {
        taskService.deleteTask(id);
        return "redirect:/";
    }
    @PostMapping("/create-task")
    public String createTask(
            @Valid @ModelAttribute("taskDTO") TaskDTO taskDTO,
            BindingResult bindingResult,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER + "") int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE + "") int size,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("tasks", taskService.getAllTasks(page, size));
            model.addAttribute("statusValues", Status.values());
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            bindingResult.getAllErrors().forEach(error ->
                    System.out.println("Ошибка валидации: " + error.getDefaultMessage()));
            return "index";
        }
        taskService.createTask(taskDTO);
        return "redirect:/";
    }
    @PostMapping("/update-description")
    public String updateDescription(
            @RequestParam Long taskId,
            @RequestParam @NotBlank @Size(max = 100, message = "Описание должно быть от 1 до 100 символов") String description,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER + "") int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE + "") int size,
            RedirectAttributes redirectAttributes) {

        try {
            taskService.updateDescription(taskId, description);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("descriptionError", "Ошибка при обновлении: " + e.getMessage());
            redirectAttributes.addFlashAttribute("descriptionTaskId", taskId);
        }

        return "redirect:/?page=" + page + "&size=" + size;
    }
}
