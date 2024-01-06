package catan.ceng.catanui.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Jackson MixIn class for the Player class to customize JSON deserialization.
 */
public abstract class PlayerMixIn {

    /**
     * Constructor to customize JSON deserialization of the Player class.
     *
     * @param username The username property from JSON.
     * @param score    The score property from JSON.
     */
    PlayerMixIn(@JsonProperty("userName") String username,
                @JsonProperty("score") Integer score) {
    }
}