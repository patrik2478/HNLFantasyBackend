package hr.fantasy.hnl.domain;

import lombok.Data;

@Data
public class Team {

    private Long id;
    private String name;
    private String photoUrl;

    public Team(Long id, String name, String photoUrl) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;

    }

    public Team() {
    }
}
