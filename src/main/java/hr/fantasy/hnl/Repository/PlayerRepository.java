package hr.fantasy.hnl.Repository;

import hr.fantasy.hnl.Domain.Player;

import java.util.List;
import java.util.Optional;

interface PlayerRepository {
    List<Player> getAll();

    Optional<Player> findPlayerByName(String playerName);
}

