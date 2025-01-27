package lk.ijse.gdse.instritutefirstsemfinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent load = FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"));
        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.setTitle("StudyHall - Login");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/image/appLogo.jpg")));
        stage.show();
        stage.setResizable(false);
    }
}
