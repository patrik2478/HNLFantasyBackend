package hr.fantasy.hnl.dto;

import lombok.Data;

@Data
public class PlayerDTO {
    private String firstName;
    private String lastName;
    private Integer age;

    public PlayerDTO(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }



    public PlayerDTO() {
    }

    public String toString() {
        return "PlayerDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName=" + lastName +
                ", age=" + age +
                '}';
    }
}
