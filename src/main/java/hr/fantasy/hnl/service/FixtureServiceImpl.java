package hr.fantasy.hnl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fantasy.hnl.domain.Fixture;
import hr.fantasy.hnl.externalService.FixtureExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FixtureServiceImpl implements FixtureService {

    private final FixtureExternalApiService externalApiService;
    private final ObjectMapper objectMapper;

    @Autowired
    public FixtureServiceImpl(FixtureExternalApiService externalApiService, ObjectMapper objectMapper) {
        this.externalApiService = externalApiService;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Fixture> getTeamFixtures(Long teamId) throws IOException, InterruptedException {
        String json = externalApiService.getTeamFixtures(teamId);
        List<Fixture> fixtures = new ArrayList<>();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        parseListOfFixtures(json, fixtures);

        return fixtures;
    }

    @Override
    public List<Fixture> getLastGameWeek() throws IOException, InterruptedException {
        String json = externalApiService.getLastGameWeek();
        List<Fixture> fixtures = new ArrayList<>();
        parseListOfFixtures(json, fixtures);
        return fixtures;
    }

    private void parseListOfFixtures(String json, List<Fixture> fixtures) {
        try {
            Map<String, Object> responseMap = objectMapper.readValue(json, Map.class);
            List<Map<String, Object>> fixturesData = (List<Map<String, Object>>) responseMap.get("response");

            for (Map<String, Object> fixtureData : fixturesData) {
                Fixture fixture = new Fixture();

                Map<String, Object> fixtureInfo = (Map<String, Object>) fixtureData.get("fixture");
                fixture.setId((int) fixtureInfo.get("id"));
                fixture.setVenue((String) ((Map<String, Object>) fixtureInfo.get("venue")).get("name"));

                Map<String, Object> teamScores = (Map<String, Object>) fixtureData.get("goals");

                Map<String, Object> teams = (Map<String, Object>) fixtureData.get("teams");
                Map<String, Object> homeTeam = (Map<String, Object>) teams.get("home");
                Map<String, Object> awayTeam = (Map<String, Object>) teams.get("away");

                Map<String, Object> teamScoresMap = new HashMap<>();
                teamScoresMap.put("homeTeam", (String) homeTeam.get("name"));
                teamScoresMap.put("homeScore", (Integer) teamScores.get("home"));
                teamScoresMap.put("awayTeam", (String) awayTeam.get("name"));
                teamScoresMap.put("awayScore", (Integer) teamScores.get("away"));

                fixture.setTeamScores(teamScoresMap);

                fixtures.add(fixture);
            }
        } catch (JsonProcessingException e) {
        }
    }


}
