package hr.fantasy.hnl.externalService;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.List;

@Data
@RestController
public class PlayerExternalApiService {

    private RestTemplate restTemplate;

    @Value("${api.football.key.value}")
    private String apiKey;

    @Value("${api.football.host.value}")
    private String apiHost;

    @Value("${api.default.season}")
    private String defaultSeason;

    @Value("${api.default.league}")
    private String defaultLeagueId;

    private String responseBody;
    private int totalPages;
    private int currentPage;

    @GetMapping(value = "/playerById", produces = "application/json")
    public String getPlayerById(@RequestParam String id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-football-v1.p.rapidapi.com/v3/players?id="+id+"&season="+defaultSeason+""))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", apiHost)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getPlayerApiResponse(String teamID) throws IOException, InterruptedException {
        String baseUrl = "https://api-football-v1.p.rapidapi.com/v3/players";
        String season = "2022";

        List<String> allPlayerData = new ArrayList<>();

        int currentPage = 1;
        int totalPages = Integer.MAX_VALUE;

        ObjectMapper objectMapper = new ObjectMapper();

        while (currentPage <= totalPages) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "?team=" + teamID + "&league=" + defaultLeagueId + "&season=" + season + "&page=" + currentPage))
                    .header("x-rapidapi-key", apiKey)
                    .header("x-rapidapi-host", apiHost)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the response body as JSON
            JsonNode responseJson = objectMapper.readTree(response.body());
            JsonNode pagingJson = responseJson.get("paging");
            int current = pagingJson.get("current").asInt();
            totalPages = pagingJson.get("total").asInt();

            // Add the response body to the list
            allPlayerData.add(response.body());

            currentPage = current + 1;
        }

        // Aggregate the data from all paginations
        String aggregatedResponse = "[" + String.join(",", allPlayerData) + "]";

        return aggregatedResponse;
    }

}
