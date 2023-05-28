package hr.fantasy.hnl.service;

import hr.fantasy.hnl.domain.Team;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface TeamService {

    public List<Team> getAllTeams() throws IOException, InterruptedException, ParseException;
}
