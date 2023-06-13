package hr.fantasy.hnl.service;

import hr.fantasy.hnl.domain.Fixture;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface FixtureService {
    List<Fixture> getTeamFixtures(Long teamId) throws IOException, InterruptedException;
    List<Fixture> getLastGameWeek() throws IOException, InterruptedException;
}
