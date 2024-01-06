package catan.ceng.catanui;

import catan.ceng.catanui.application.MainApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * The main entry point for the Catan UI application.
 * This class is annotated with {@code @SpringBootApplication} and serves as the main class for starting the application.
 */
@SpringBootApplication
public class CatanUiApplication {
    /**
     * The main method of the Catan UI application.
     * It launches the JavaFX application by calling {@link Application#launch(Class, String...)} with {@link MainApplication} as the main class.
     *
     * @param args Command-line arguments (if any) passed to the application.
     */
    public static void main(String[] args) {
        Application.launch(MainApplication.class, args);
    }

}
