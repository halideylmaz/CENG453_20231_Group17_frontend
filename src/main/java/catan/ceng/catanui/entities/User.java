package catan.ceng.catanui.entities;


import lombok.Data;

@Data
public class User {

    private  String username;
    private Integer score;

    public User(String username, Integer score) {
        this.username = username;
        this.score = score;
    }
}
