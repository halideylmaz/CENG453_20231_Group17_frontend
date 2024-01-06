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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This service class handles HTTP requests to a RESTful API for user login using the OkHttpClient library.
 * It sends a POST request with JSON data to the specified URL and handles the response.
 */
@Service
@Slf4j
public class RequestService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final OkHttpClient client = new OkHttpClient();

    // The base URL for the REST API
    private static String REST_URL="http://127.0.0.1:8080/";

    /**
     * Attempts to log in a player by sending a POST request to the login API.
     *
     * @param player The player object containing username and password for login.
     * @return True if the login is successful, false otherwise, or null in case of an exception.
     */
    @SneakyThrows
    public Boolean login(Player player) {
        try {
            String loginUrl = REST_URL.concat("api/login");
            URL url = new URL(loginUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String name = player.getUserName();
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

        } catch (IOException e) {
            log.error("Exception during login:", e);
            return null;
        }
    }

    /**
     * Attempts to register a new player by sending a POST request to the registration API.
     *
     * @param player The player object containing username, email, and password for registration.
     * @return True if the registration is successful, false otherwise, or null in case of an exception.
     */
    @SneakyThrows
    public Boolean register(Player player) {
        try {
            String registerUrl = REST_URL.concat("api/register");
            URL url = new URL(registerUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String name = player.getUserName();
            String email = player.getEmail();
            String password = player.getPassword();

            String input = "{\"email\":\"";
            input = input.concat(email);
            input = input.concat("\",\"userName\":\"");
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
                log.info(response.toString());
                return true;
            } else {
                log.error(String.valueOf(responseCode));
                conn.disconnect();
                return false; // or throw an exception based on your error handling strategy
            }

        } catch (IOException e) {
            log.error("Exception during register:", e);
            return null;
        }
    }

    /**
     * Sends a request to reset the user's password by sending a POST request to the reset password API.
     *
     * @param email The email address of the user for password reset.
     * @return True if the reset email is sent successfully, false otherwise, or false in case of an exception.
     */
    @SneakyThrows
    public Boolean sendResetEmail(String email) {
        try {
            String resetPasswordUrl = REST_URL + "resetPassword?email=" + URLEncoder.encode(email, "UTF-8");
            URL url = new URL(resetPasswordUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            os.flush();
            log.info(resetPasswordUrl);
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;
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
                    log.info("Mail sent, check your email!");
                    return true; // or return a default/fake Player object
                }
            } else {
                // Handle non-successful response code (e.g., log error)
                log.error("Failed : HTTP error code : {}", conn.getResponseMessage());
                conn.disconnect();
                return false; // or throw an exception based on your error handling strategy
            }

        } catch (IOException e) {
            log.error("Exception during password reset:", e);
            return false;
        }
    }

    /**
     * Sends a request to reset the user's password by sending a POST request to the reset password API.
     *
     * @param token       The token received in the password reset email.
     * @param newPassword The new password to be set.
     * @return True if the password is reset successfully, false otherwise, or false in case of an exception.
     */
    @SneakyThrows
    public Boolean resetPassword(String token, String newPassword) {
        try {
            String resetPasswordUrl = REST_URL + "resetPassword/reset?token=" + URLEncoder.encode(token, "UTF-8") +
                    "&newPassword=" + URLEncoder.encode(newPassword, "UTF-8");

            URL url = new URL(resetPasswordUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                // Read the response body if needed
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String output;

                while ((output = br.readLine()) != null) {
                    response.append(output);
                }

                conn.disconnect();

                // Handle the response based on your requirements
                log.info("Password reset request successful!");
                log.info("Response: " + response.toString());

                return true;
            } else {
                // Handle non-successful response code (e.g., log error)
                log.error("Failed : HTTP error code : {}", conn.getResponseMessage());
                conn.disconnect();
                return false;
            }
        } catch (IOException e) {
            log.error("Exception during password reset:", e);
            return false;
        }
    }

    /**
     * Retrieves the scoreboard data from the server based on the specified time frame.
     *
     * @param time The time frame for which the scoreboard data should be retrieved (e.g., "weekly" or "monthly").
     * @return A JSON string representing the scoreboard data if successful, or null in case of an exception.
     */
    public String getScoreboard(String time) {
        try {
            String leaderBoardURL = REST_URL + "leaderboard";
            if (time.equals("weekly")) {
                leaderBoardURL = leaderBoardURL.concat("/lastweek");
            } else if (time.equals("monthly")) {
                leaderBoardURL = leaderBoardURL.concat("/lastmonth");
            }
            URL url = new URL(leaderBoardURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            String scoreBoard = "";
            while ((output = br.readLine()) != null) {
                scoreBoard = scoreBoard.concat(output);
            }

            conn.disconnect();

            return scoreBoard;

        } catch (IOException e) {

            e.printStackTrace();

            return null;

        }
    }

    /**
     * Sends a request to add a new score to the server.
     *
     * @param userName The name of the user who achieved the score.
     * @param score    The score achieved by the user.
     */
    public void addScore(String userName, String score) {
        try {

            String scoreUrl = REST_URL + "scores";
            URL url = new URL(scoreUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            String formattedDate = now.format(formatter);

            String input = "{\"userName\":\"";
            input = input.concat(userName);
            input = input.concat("\",\"score\":");
            input = input.concat(score);
            input = input.concat(",\"date\":\"");
            input = input.concat(formattedDate);
            input = input.concat("\"}");

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;
                StringBuilder response = new StringBuilder();

                while ((output = br.readLine()) != null) {
                    response.append(output);
                }

                conn.disconnect();
                log.info(response.toString());
            } else {
                log.error(String.valueOf(conn.getResponseMessage()));
                conn.disconnect();
            }

        } catch (IOException e) {
            log.error("Exception during addScore:", e);
        }

    }
}
