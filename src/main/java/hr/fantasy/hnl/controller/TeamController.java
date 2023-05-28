package hr.fantasy.hnl.controller;

import hr.fantasy.hnl.domain.Team;
import hr.fantasy.hnl.service.TeamService;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public String showTeamPage(Model model) throws IOException, ParseException, InterruptedException {
        List<Team> teams = teamService.getAllTeams();
        model.addAttribute("teams",teams);
        return "team";
    }
}
