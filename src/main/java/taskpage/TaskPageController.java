package taskpage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class TaskPageController {
    @GetMapping("/taskpage")
    public String taskPageForm(Model model) {System.out.println("taskPageForm() called.");
        model.addAttribute("taskPage", new TaskPage());
        return "request";
    }
    
    @PostMapping("/taskpage")
    public String taskPageSubmit(@ModelAttribute TaskPage taskPage) {
        return "result";
    }
    
    @PutMapping("/taskpage")
    public String thatSItSubmit(Model model) {System.out.println("thatSItSubmit() happened.");
        model.addAttribute("user", new User("persona", "CAIVS IVLIVS CAESAR", "Roma"));
        return "thatsit";
    }
}
