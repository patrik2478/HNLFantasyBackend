package hr.fantasy.hnl.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fantasy.hnl.domain.Team;
import hr.fantasy.hnl.externalService.TeamExternalApiService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    private TeamExternalApiService teamExternalApiService;

    public TeamServiceImpl(TeamExternalApiService teamExternalApiService) {
        this.teamExternalApiService = teamExternalApiService;
    }

    @Override
    public List<Team> getAllTeams() throws IOException, InterruptedException {
        List<Team> allTeamNames = parseJsonResponse(teamExternalApiService.teamAllTeams());

        return allTeamNames;
    }

    private List<Team> parseJsonResponse(String jsonString) {
        List<Team> teams = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode responseNode = jsonNode.get("response");

            for (JsonNode teamNode : responseNode) {
                Team team = new Team();
                team.setName(teamNode.get("team").get("name").asText());
                team.setId(teamNode.get("team").get("id").asLong());
                team.setPhotoUrl(teamNode.get("team").get("logo").asText());
                teams.add(team);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teams;
    }
}
