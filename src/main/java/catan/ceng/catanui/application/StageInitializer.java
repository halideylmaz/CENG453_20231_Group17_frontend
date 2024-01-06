package catan.ceng.catanui.application;

import catan.ceng.catanui.application.MainApplication.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import catan.ceng.catanui.entities.GameConstants;

import java.io.IOException;
import java.net.URL;

/**
 * This class is a Spring component that initializes the JavaFX stage when a {@link StageReadyEvent} is triggered.
 * It loads the main FXML resource and sets up the JavaFX stage with the loaded content.
 * Implements the {@link ApplicationListener} interface to listen for stage readiness events.
 */
@Component
@Slf4j
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Value("classpath:/fxml/mainmenu.fxml")
    private URL mainResource;

    /**
     * Called when a {@link StageReadyEvent} is published, indicating that the JavaFX stage is ready.
     * It loads the main FXML resource, sets up the stage, and displays it.
     *
     * @param event The {@link StageReadyEvent} triggered when the stage is ready.
     */
    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader=new FXMLLoader(mainResource.openConnection().getURL());
            VBox parent=fxmlLoader.load();

            Stage stage=event.getStage();
            stage.setTitle("Catan Game");
            Scene mainScene=new Scene(parent,800,600);
            stage.setScene(mainScene);

            GameConstants.stage = stage;
            stage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
