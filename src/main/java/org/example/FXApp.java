package org.example;

import db.DatabaseInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // ensure DB schema/tables exist before controllers attempt to use them
        DatabaseInitializer.initialize();

        FXMLLoader loader = new FXMLLoader(
                // FXML is located at the classpath root (src/main/resources), so use an absolute path
                FXApp.class.getResource("/main_view.fxml")
        );

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Tuukka Uusitalo, Shopping Cart");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}