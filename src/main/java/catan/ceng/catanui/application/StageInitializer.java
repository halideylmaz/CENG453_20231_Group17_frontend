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

import catan.ceng.catanui.entities.StageEntity;

import java.io.IOException;
import java.net.URL;

@Component
@Slf4j
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Value("classpath:/fxml/mainmenu.fxml")
    private URL mainResource;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader=new FXMLLoader(mainResource.openConnection().getURL());
            VBox parent=fxmlLoader.load();

            Stage stage=event.getStage();
            stage.setTitle("Catan Game");
            Scene mainScene=new Scene(parent,800,600);
            stage.setScene(mainScene);

            StageEntity.stage = stage;
            stage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
