package hr.fantasy.hnl.service;

import hr.fantasy.hnl.domain.League;
import org.springframework.stereotype.Service;

@Service
public interface LeagueService {
    League getDefaultLeague();
}
