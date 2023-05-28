package hr.fantasy.hnl.domain;

import lombok.Data;

@Data
public class PlayerStats {
    private int appearences;
    private int lineups;
    private int minutes;
    private String position;
    private double rating;
    private int goals;
    private int assists;
    private int yellowCards;
    private int redCards;
}
