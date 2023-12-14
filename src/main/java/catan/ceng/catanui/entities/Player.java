package catan.ceng.catanui.entities;


import lombok.Data;

@Data
public class Player {

    private  String username;
    private Integer score;

    public Player(String username, Integer score) {
        this.username = username;
        this.score = score;
    }
}
