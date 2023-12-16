package catan.ceng.catanui.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class PlayerMixIn {

    PlayerMixIn(@JsonProperty("username") String username,
                @JsonProperty("score") Integer score) {
    }
}