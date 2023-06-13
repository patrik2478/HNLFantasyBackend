package hr.fantasy.hnl.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fantasy.hnl.domain.League;
import hr.fantasy.hnl.externalService.LeagueExternalApiService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LeagueServiceImpl implements LeagueService {
    private final LeagueExternalApiService leagueExternalApiService;

    public LeagueServiceImpl(LeagueExternalApiService leagueExternalApiService) {
        this.leagueExternalApiService = leagueExternalApiService;
    }

    @Override
    public League getDefaultLeague() {
        try {
            String leagueJson = leagueExternalApiService.getLeagueById();
            return parseLeagueJsonResponse(leagueJson);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private League parseLeagueJsonResponse(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode responseNode = jsonNode.get("response");

            if (responseNode.isArray() && responseNode.size() > 0) {
                JsonNode leagueNode = responseNode.get(0).get("league");
                League league = new League();
                league.setId(leagueNode.get("id").asLong());
                league.setName(leagueNode.get("name").asText());
                league.setPhotoUrl(leagueNode.get("logo").asText());
                // Add more properties as needed
                return league;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
