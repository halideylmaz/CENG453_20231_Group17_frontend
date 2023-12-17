package catan.ceng.catanui.entities;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {

    private Long id;
    private String userName;
    private String email;
    private String password;
    private Long score;

}

// Add methods to modify player state based on game actions
