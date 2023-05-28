package hr.fantasy.hnl.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fantasy.hnl.domain.Player;
import hr.fantasy.hnl.domain.PlayerStats;
import hr.fantasy.hnl.domain.Team;
import hr.fantasy.hnl.dto.PlayerDTO;
import hr.fantasy.hnl.externalService.PlayerExternalApiService;
import hr.fantasy.hnl.repository.PlayerRepositoryImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private PlayerRepositoryImpl playerRepository;

    private PlayerExternalApiService playerExternalApiService;


    public PlayerServiceImpl(PlayerRepositoryImpl playerRepository, PlayerExternalApiService playerExternalApiService ) {
        this.playerRepository = playerRepository;
        this.playerExternalApiService = playerExternalApiService;
    }

    @Override
    public List<PlayerDTO> getAll() {
        return playerRepository.getAll().stream().map(this::mapPlayerToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<PlayerDTO> findPlayerByName(String firstName) {
        return Optional.ofNullable(playerRepository.findPlayerByName(firstName).map(this::mapPlayerToDTO).orElse(null));
    }

    private PlayerDTO mapPlayerToDTO(final Player player) {
        return new PlayerDTO(player.getFirstName(), player.getLastName(),player.getAge());
    }

    @Override
    public Player getPlayerById(String playerID) throws IOException, InterruptedException {
        String jsonResponse = playerExternalApiService.getPlayerById(playerID);
        Player player = parseJsonResponseForPlayer(jsonResponse);
        return player;
    }

    @Override
    public List<Player> getPlayersFromTeam(String teamID) throws IOException, InterruptedException {
        String jsonResponse = playerExternalApiService.getPlayerApiResponse(teamID);
        List<Player> allPlayersFromTeam = parseJsonResponseForPlayersFromTeam(jsonResponse);
        return allPlayersFromTeam;
    }



    private List<Player> parseJsonResponseForPlayersFromTeam(String jsonString) {
        List<Player> players = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // Iterate over each element in jsonNode
            for (JsonNode elementNode : jsonNode) {
                JsonNode responseNode = elementNode.get("response");

                if (responseNode != null && responseNode.isArray()) {
                    for (JsonNode playerResponseNode : responseNode) {
                        JsonNode playerNode = playerResponseNode.get("player");

                        Player player = new Player();
                        player.setFirstName(playerNode.get("firstname").asText());
                        player.setLastName(playerNode.get("lastname").asText());
                        player.setAge(playerNode.get("age").asInt());
                        player.setId(playerNode.get("id").asLong());
                        player.setPhotoUrl(playerNode.get("photo").asText());
                        player.setNationality(playerNode.get("nationality").asText());
                        players.add(player);
                        if(players.size() > 2) {
                            if (players.get(players.size() - 2).getId().equals(player.getId())) {
                                players.remove(players.size() - 2);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }

    private Player parseJsonResponseForPlayer(String jsonString) {
        Player player = null;

        try {
            // Create an instance of ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Read the JSON string and convert it to a JsonNode
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // Extract the "response" node from the JsonNode
            JsonNode responseNode = jsonNode.get("response");

            // Check if the response contains a player node
            if (responseNode.isArray() && responseNode.size() > 0) {
                // Get the first player node
                JsonNode playerNode = responseNode.get(0);

                // Create a new Player object
                player = new Player();

                // Extract and set the player's first name, last name, and photo URL from the playerNode
                player.setFirstName(playerNode.get("player").get("firstname").asText());
                player.setLastName(playerNode.get("player").get("lastname").asText());
                player.setPhotoUrl(playerNode.get("player").get("photo").asText());

                // Extract and set the team information from the statistics node
                JsonNode statisticsNode = playerNode.get("statistics").get(0);
                JsonNode teamNode = statisticsNode.get("team");

                Team team = new Team();
                team.setId(teamNode.get("id").asLong());
                team.setName(teamNode.get("name").asText());
                team.setPhotoUrl(teamNode.get("logo").asText());

                // Set the player's team
                player.setTeam(team);

                // Extract and set the player's statistics
                PlayerStats playerStats = new PlayerStats();
                playerStats.setAppearences(statisticsNode.get("games").get("appearences").asInt());
                playerStats.setLineups(statisticsNode.get("games").get("lineups").asInt());
                playerStats.setMinutes(statisticsNode.get("games").get("minutes").asInt());
                playerStats.setPosition(statisticsNode.get("games").get("position").asText());
                playerStats.setRating(statisticsNode.get("games").get("rating").asDouble());
                playerStats.setGoals(statisticsNode.get("goals").get("total").asInt());
                playerStats.setAssists(statisticsNode.get("goals").get("assists").asInt());
                playerStats.setYellowCards(statisticsNode.get("cards").get("yellow").asInt());
                playerStats.setRedCards(statisticsNode.get("cards").get("red").asInt());

                // Set the player's statistics
                player.setStats(playerStats);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the Player object
        return player;
    }



}
