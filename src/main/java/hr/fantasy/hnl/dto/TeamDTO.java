package hr.fantasy.hnl.dto;

import lombok.Data;

@Data
public class TeamDTO {
    private String name;

    public TeamDTO(String firstName, String lastName, Integer age) {
        this.name = name;
    }



    public TeamDTO() {
    }

    public String toString() {
        return "TeamDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
