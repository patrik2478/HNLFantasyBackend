package hr.fantasy.hnl.domain;

import lombok.Data;

@Data
public class Player {

    private Long id;
    private String firstName;
    private String lastName;
    private String photoUrl;
    private String nationality;
    private Integer age;
    private int height;
    private Team team;
    private PlayerStats stats;
    private String birthDate;

    public Player(String firstName, String lastName, Integer age, String position, String photoUrl, String nationality, Team team, PlayerStats stats) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.age = age;
        this.photoUrl = photoUrl;
        this.team = team;
        this.stats = stats;
    }

    public Player(){}

}
