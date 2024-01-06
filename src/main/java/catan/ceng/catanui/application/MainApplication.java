package catan.ceng.catanui.application;

import catan.ceng.catanui.CatanUiApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
/**
 * The main application class for the JavaFX application.
 * This class extends the JavaFX {@link Application} class and is responsible for initializing,
 * starting, and stopping the application.
 */
public class MainApplication extends Application {
    private ConfigurableApplicationContext applicationContext;

    /**
     * Initializes the application. This method is called before the {@link #start(Stage)} method.
     * It creates and initializes the Spring application context.
     */
    @Override
    public void init() {
        applicationContext=new SpringApplicationBuilder(CatanUiApplication.class)
                .run();
    }

    /**
     * Starts the JavaFX application. This method is called after the {@link #init()} method.
     * It publishes a custom event to indicate that the application's main stage is ready.
     *
     * @param stage The primary stage for the JavaFX application.
     */
    @Override
    public void start(Stage stage){
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    /**
     * Stops the application. This method is called when the JavaFX application is shut down.
     * It closes the Spring application context and exits the JavaFX platform.
     */
    @Override
    public void stop(){
        applicationContext.close();
        Platform.exit();
    }

    /**
     * Custom event class representing the readiness of the JavaFX stage.
     * Extends {@link ApplicationEvent}.
     */
    static class StageReadyEvent extends ApplicationEvent {
         /** Constructs a new StageReadyEvent with the given JavaFX stage.
                *
                * @param stage The JavaFX stage that is ready.
         */
        public StageReadyEvent(Stage stage) {
            super(stage);
        }
        /**
         * Gets the JavaFX stage associated with this event.
         *
         * @return The JavaFX stage.
         */
        public Stage getStage(){
            return ((Stage) getSource());
        }
    }
}
