package hr.fantasy.hnl.service;

import hr.fantasy.hnl.domain.Player;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface PlayerService {

    public List<Player> getPlayersFromTeam(String teamID) throws IOException, InterruptedException;

    public Player getPlayerById(String playerID) throws IOException, InterruptedException;

}
