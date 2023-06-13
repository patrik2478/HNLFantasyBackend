package hr.fantasy.hnl.controller;

import hr.fantasy.hnl.domain.League;
import hr.fantasy.hnl.service.LeagueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private LeagueService leagueService;

    public HomeController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        League league = leagueService.getDefaultLeague();
        model.addAttribute("league", league);
        return "home";
    }

}
