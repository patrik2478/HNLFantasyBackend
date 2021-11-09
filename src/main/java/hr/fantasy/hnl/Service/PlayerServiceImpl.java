package hr.fantasy.hnl.Service;

import hr.fantasy.hnl.Domain.Player;
import hr.fantasy.hnl.Dto.PlayerDTO;
import hr.fantasy.hnl.Repository.PlayerRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private PlayerRepositoryImpl playerRepository;


    public PlayerServiceImpl(PlayerRepositoryImpl playerRepository) {
        this.playerRepository = playerRepository;
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
}
