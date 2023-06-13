package hr.fantasy.hnl.domain;

import lombok.Data;

import java.util.Map;

@Data
public class Fixture {

    private int id;
    private String venue;
    private Map<String, Object> teamScores;

    public Fixture(int id, String venue, Map<String, Object> teamScores) {
        this.id = id;
        this.venue = venue;
        this.teamScores = teamScores;
    }

    public Fixture() {
    }
}
