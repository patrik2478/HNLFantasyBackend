package hr.fantasy.hnl.externalService;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Data
@RestController
public class StandingExternalApiService {
    private RestTemplate restTemplate;

    @Value("${api.football.key.value}")
    private String apiKey;

    @Value("${api.football.host.value}")
    private String apiHost;

    @Value("${api.default.league}")
    private String defaultLeagueId;

    @Value("${api.default.season}")
    private String defaultSeason;

    @GetMapping(value = "/getStandings", produces = "application/json")
    public String getStandings() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-football-v1.p.rapidapi.com/v3/standings?season="+defaultSeason+"&league="+defaultLeagueId+""))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", apiHost)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
