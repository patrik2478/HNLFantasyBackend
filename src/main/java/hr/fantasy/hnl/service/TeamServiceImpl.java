package hr.fantasy.hnl.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fantasy.hnl.domain.Team;
import hr.fantasy.hnl.externalService.StandingExternalApiService;
import hr.fantasy.hnl.externalService.TeamExternalApiService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    private TeamExternalApiService teamExternalApiService;
    private StandingExternalApiService standingExternalApiService;
    private FixtureService fixtureService;

    public TeamServiceImpl(TeamExternalApiService teamExternalApiService, FixtureService fixtureService, StandingExternalApiService standingExternalApiService) {
        this.teamExternalApiService = teamExternalApiService;
        this.fixtureService = fixtureService;
        this.standingExternalApiService = standingExternalApiService;
    }

    @Override
    public List<Team> getAllTeams() throws IOException, InterruptedException {
        List<Team> allTeamNames = parseAllTeamsJsonResponse(teamExternalApiService.teamAllTeams());
        for(int i = 0 ; i < allTeamNames.size(); i++){
            allTeamNames.get(i).setFixtures(fixtureService.getTeamFixtures(allTeamNames.get(i).getId()));
        }
        parseJsonResponseStandings(standingExternalApiService.getStandings(), allTeamNames);
        sortTeamsByPointsDescending(allTeamNames);
        return allTeamNames;
    }

    @Override
    public Team getTeamById(Long teamId) throws IOException, InterruptedException {
        String teamJson = teamExternalApiService.getTeamById(teamId);
        Team team =  parseTeamJsonResponse(teamJson);
        team.setFixtures(fixtureService.getTeamFixtures(team.getId()));
        return team;
    }

    private List<Team> parseAllTeamsJsonResponse(String jsonString) {
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

    private Team parseTeamJsonResponse(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode responseNode = jsonNode.get("response");

            if (responseNode.isArray() && responseNode.size() > 0) {
                JsonNode teamNode = responseNode.get(0);
                Team team = new Team();
                team.setName(teamNode.get("team").get("name").asText());
                team.setId(teamNode.get("team").get("id").asLong());
                team.setPhotoUrl(teamNode.get("team").get("logo").asText());
                return team;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseJsonResponseStandings(String jsonString, List<Team> teams) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode responseNode = jsonNode.get("response");
            JsonNode leagueNode = responseNode.get(0).get("league");
            JsonNode standingsNode = leagueNode.get("standings").get(0);

            for (JsonNode teamNode : standingsNode) {
                long teamId = teamNode.get("team").get("id").asLong();
                for (Team team : teams) {
                    if (team.getId().equals(teamId)) {
                        team.setForm(teamNode.get("form").asText());
                        team.setPoints(teamNode.get("points").asInt());
                        team.setGoalsDif(teamNode.get("goalsDiff").asInt());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sortTeamsByPointsDescending(List<Team> teams) {
        Collections.sort(teams, new Comparator<Team>() {
            @Override
            public int compare(Team team1, Team team2) {
                return Integer.compare(team2.getPoints(), team1.getPoints());
            }
        });
    }

}
