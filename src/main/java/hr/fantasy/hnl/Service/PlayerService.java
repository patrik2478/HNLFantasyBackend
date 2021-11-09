package hr.fantasy.hnl.Service;

import hr.fantasy.hnl.Dto.PlayerDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PlayerService {

    List<PlayerDTO> getAll();

    Optional<PlayerDTO> findPlayerByName(String firstName);
}
