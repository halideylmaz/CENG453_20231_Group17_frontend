package catan.ceng.catanui;

import catan.ceng.catanui.application.MainApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatanUiApplication {

    public static void main(String[] args) {
        Application.launch(MainApplication.class, args);
    }

}
