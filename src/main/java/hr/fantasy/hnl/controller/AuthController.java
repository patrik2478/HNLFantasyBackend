package hr.fantasy.hnl.controller;

import hr.fantasy.hnl.domain.User;
import hr.fantasy.hnl.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
@SessionAttributes("username")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, HttpSession session) {
        userService.register(user);
        session.setAttribute("username", user.getUsername()); // Store the username in the session
        return "redirect:/teams";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, Model model, HttpSession session) {
        boolean authenticated = userService.authenticate(user);

        if (authenticated) {
            session.setAttribute("username", user.getUsername());
            return "redirect:/teams";
        } else {
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate(); // Invalidate the session to log out the user
        return "redirect:/login";
    }
}
