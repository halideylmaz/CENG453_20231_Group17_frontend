package catan.ceng.catanui;

import catan.ceng.catanui.MainApplication.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Value("classpath:/fxml/main.fxml")
    private URL mainResource;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader=new FXMLLoader(mainResource.openConnection().getURL());
            BorderPane parent=fxmlLoader.load();

            Stage stage=event.getStage();
            stage.setTitle("Catan Game");
            stage.setScene(new Scene(parent,800,600));
            stage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
