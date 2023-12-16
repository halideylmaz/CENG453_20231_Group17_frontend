package catan.ceng.catanui.service;

import catan.ceng.catanui.entities.Player;
import catan.ceng.catanui.entities.PlayerMixIn;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

@Service
@Slf4j
public class RequestService {
    private static final ObjectMapper objectMapper= new ObjectMapper();
    private static final OkHttpClient client=new OkHttpClient();

    private static String REST_URL="http://127.0.0.1:8080/";
    @SneakyThrows
    public static Boolean login(Player player) {
        try {
            String loginUrl = REST_URL.concat("api/login");
            URL url = new URL(loginUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String name = player.getUsername();
            String password = player.getPassword();

            String input = "{\"username\":\"";
            input = input.concat(name);
            input = input.concat("\",\"password\":\"");
            input = input.concat(password);
            input = input.concat("\"}");
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;
                //Player player1;
                StringBuilder response = new StringBuilder();

                while ((output = br.readLine()) != null) {
                    response.append(output);
                }

                conn.disconnect();

                if (response.toString().startsWith("{")) {
                    // It's JSON, proceed with deserialization
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
                    mapper.addMixIn(Player.class, PlayerMixIn.class);
                    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

                    return true;
                } else {
                    // It's plain text, handle the success message or any other logic
                    log.info("Login successful!");
                    return true; // or return a default/fake Player object
                }
            } else {
                // Handle non-successful response code (e.g., log error)
                log.error("Failed : HTTP error code : {}", responseCode);
                conn.disconnect();
                return false; // or throw an exception based on your error handling strategy
            }

        }
        catch (IOException e) {
            log.error("Exception during login:", e);
            return null;
        }
    }




}
