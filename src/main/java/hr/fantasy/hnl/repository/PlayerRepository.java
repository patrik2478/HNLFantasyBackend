package hr.fantasy.hnl.repository;

import hr.fantasy.hnl.domain.Player;

import java.util.List;
import java.util.Optional;

interface PlayerRepository {
    List<Player> getAll();

    Optional<Player> findPlayerByName(String playerName);
}

