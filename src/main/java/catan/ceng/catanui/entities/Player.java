package catan.ceng.catanui.entities;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {

    private Long id;
    private String username;
    private String password; /* In BCrpyt encoded form */
    private Long totalScore;


}
