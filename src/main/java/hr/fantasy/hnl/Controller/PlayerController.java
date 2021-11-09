package hr.fantasy.hnl.Controller;


import hr.fantasy.hnl.Dto.PlayerDTO;
import hr.fantasy.hnl.Service.PlayerService;
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
import java.util.List;

@Data
@RestController
public class PlayerController {

    private PlayerService playerService;

    private RestTemplate restTemplate;

    @Value("${api.football.key.value}")
    private String apiKey;

    @Value("${api.football.host.value}")
    private String apiHost;

    @Value("${api.default.league}")
    private String defaultLeagueId;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value = "", produces = "application/json")
    public List<PlayerDTO> getAll() {
        return playerService.getAll();
    }

/*
kralj
    @GetMapping(value = "/name", produces = "application/json")
    public Optional<PlayerDTO> getPlayerByName(@RequestParam String name) {
        return playerService.findPlayerByName(name);
    }
*/
    @GetMapping(value = "/playerById", produces = "application/json")
    public String getPlayerById(@RequestParam String id ,@RequestParam String season ) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-football-v1.p.rapidapi.com/v3/players?id="+id+"&season="+season+""))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", apiHost)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    @GetMapping(value = "/player", produces = "application/json")
    public String getPlayerByName(@RequestParam String playerName) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-football-v1.p.rapidapi.com/v3/players?league="+defaultLeagueId+"&search="+playerName+""))
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", apiHost)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
