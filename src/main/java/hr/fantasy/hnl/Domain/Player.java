package hr.fantasy.hnl.Domain;

import lombok.Data;

@Data
public class Player {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String position;

    public Player(String firstName, String lastName, Integer age, String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.position = position;
    }

    public Player(){}

}
