package demo.company2.controllers;

import demo.company2.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "views/list";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(defaultValue = "") String name) {
        model.addAttribute("users", userService.findByName(name));
        return "views/list";
    }
}

