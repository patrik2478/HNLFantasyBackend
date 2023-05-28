package hr.fantasy.hnl.controller;

import hr.fantasy.hnl.domain.User;
import hr.fantasy.hnl.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
            return "profilePage";
        } else {
            // Handle the case when the user is not logged in
            // For example, redirect to the login page or show an error message
            return "redirect:/login";
        }
    }
}
