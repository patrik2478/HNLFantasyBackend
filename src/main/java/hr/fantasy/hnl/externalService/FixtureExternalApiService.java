package hr.fantasy.hnl.externalService;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Data
@RestController
public class FixtureExternalApiService {
    private RestTemplate restTemplate;

    @Value("${api.football.key.value}")
    private String apiKey;

    @Value("${api.football.host.value}")
    private String apiHost;

    @Value("${api.default.league}")
    private String defaultLeagueId;

    @Value("${api.default.season}")
    private String defaultSeason;

    @GetMapping(value = "/getTeamFixtures", produces = "application/json")
    public String getTeamFixtures(@RequestParam Long teamID) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-football-v1.p.rapidapi.com/v3/fixtures?league="+defaultLeagueId+"&season="+defaultSeason+"&team="+teamID+""))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", apiHost)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    @GetMapping(value = "/getLastGameWeek", produces = "application/json")
    public String getLastGameWeek() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-football-v1.p.rapidapi.com/v3/fixtures?league="+defaultLeagueId+"&season="+defaultSeason+"&last="+5+""))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", apiHost)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
