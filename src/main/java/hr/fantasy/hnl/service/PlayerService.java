package hr.fantasy.hnl.service;

import hr.fantasy.hnl.domain.Player;
import hr.fantasy.hnl.dto.PlayerDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface PlayerService {

    List<PlayerDTO> getAll();

    Optional<PlayerDTO> findPlayerByName(String firstName);

    public List<Player> getPlayersFromTeam(String teamID) throws IOException, InterruptedException;

    public Player getPlayerById(String playerID) throws IOException, InterruptedException;

}
