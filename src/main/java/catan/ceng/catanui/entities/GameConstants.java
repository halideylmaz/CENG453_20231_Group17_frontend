package catan.ceng.catanui.entities;
import catan.ceng.catanui.entities.CatanGame;
import javafx.stage.Stage;
/**
 * A utility class that stores various game-related constants and variables.
 */
public class GameConstants {
    /**
     * The username of the logged in player.
     */
    public static String username;
    /**
     * The username of player 2 (if applicable).
     */
    public static String username_player2;
    /**
     * The username of player 3 (if applicable).
     */
    public static String username_player3;
    /**
     * The username of player 4 (if applicable).
     */
    public static String username_player4;
    /**
     * A boolean flag indicating whether the game is played against an AI opponent.
     */
    public static boolean vsAI;
    /**
     * The instance of the CatanGame being played.
     */
    public static CatanGame game;
    /**
     * The JavaFX stage used in the user interface.
     */
    public static Stage stage;


}