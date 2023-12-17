package catan.ceng.catanui.entities;

import lombok.*;

/**
 * The `Player` class represents a player in the application with various attributes.
 * This class is typically used to store player information such as username, email, password,
 * and score.
 *
 * <p>It includes getter and setter methods for accessing and modifying the player's attributes.
 * The class also provides constructors for creating player objects with or without initial values.
 *
 * <p>Usage:
 * <pre>{@code
 * Player player = new Player();
 * player.setUserName("JohnDoe");
 * player.setEmail("john@example.com");
 * player.setPassword("password123");
 * player.setScore(1000L);
 * }</pre>
 *
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
 * @see lombok.Builder
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {

    /**
     * The unique identifier for the player.
     */
    private Long id;
    /**
     * The username of the player.
     */
    private String userName;
    /**
     * The email address associated with the player's account.
     */
    private String email;
    /**
     * The password for the player's account.
     */
    private String password;
    /**
     * The score representing the player's performance.
     */
    private Long score;

}

