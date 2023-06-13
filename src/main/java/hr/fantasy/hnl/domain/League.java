package hr.fantasy.hnl.domain;

import lombok.Data;

@Data
public class League {
    private Long id;
    private String name;
    private String photoUrl;

    public League(Long id, String name, String photoUrl) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public League() {
    }
}
