package taskpage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TaskPageController {
    @GetMapping("/taskpage")
    public String taskPageForm(Model model) {
        model.addAttribute("taskPage", new TaskPage());
        return "request";
    }
    
    @PostMapping("/taskpage")
    public String taskPageSubmit(@ModelAttribute TaskPage taskPage) {
        return "result";
    }
}
