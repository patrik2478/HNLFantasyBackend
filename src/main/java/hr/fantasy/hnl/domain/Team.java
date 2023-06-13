package hr.fantasy.hnl.domain;

import lombok.Data;

import java.util.List;

@Data
public class Team {

    private Long id;
    private String name;
    private String photoUrl;
    private String form;
    private int points;
    private int goalsDif;
    private List<Fixture> fixtures;

    public Team(Long id, String name, String photoUrl, String form, int points, int goalsDif, List<Fixture> fixtures) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.form = form;
        this.points = points;
        this.goalsDif = goalsDif;
        this.fixtures = fixtures;
    }

    public Team() {
    }
}
