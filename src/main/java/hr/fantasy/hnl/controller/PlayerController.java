package hr.fantasy.hnl.controller;

import hr.fantasy.hnl.domain.Player;
import hr.fantasy.hnl.domain.Team;
import hr.fantasy.hnl.service.PlayerService;
import hr.fantasy.hnl.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;
    private final TeamService teamService;

    public PlayerController(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }

    @GetMapping(value = "/{teamID}")
    public String showPlayerPage(@PathVariable String teamID, Model model) throws IOException, InterruptedException {
        List<Player> playerList = playerService.getPlayersFromTeam(teamID);
        Team team = teamService.getTeamById(Long.parseLong(teamID));

        model.addAttribute("team", team);
        model.addAttribute("fixtures", team.getFixtures());
        model.addAttribute("players", playerList);
        return "player";
    }

    @GetMapping(value = "/player/{playerID}")
    public String showPlayerProfilePage(@PathVariable String playerID, Model model) throws IOException, InterruptedException {
        Player player = playerService.getPlayerById(playerID);
        model.addAttribute("player", player);

        return "playerProfilePage";
    }
}