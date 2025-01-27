package lk.ijse.gdse.instritutefirstsemfinal.util;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class NavigationUtil {
    public static void loadPane(Class currentClass, Pane pane, String stageTitle, String resource) {
        try {
            pane.getChildren().clear();
            Pane load = FXMLLoader.load(currentClass.getResource(resource));
            pane.getChildren().add(load);
            loadPaneEffect(load);
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.setTitle("StudyHall - "+stageTitle);

        }catch (IOException e){
            e.printStackTrace();
            AlertUtil.errorAlert(currentClass,null,stageTitle);
        }
    }

    public static void loadPaneEffect(Pane load) {
        load.setScaleX(0.8);
        load.setScaleY(0.8);
        load.setOpacity(0);
        load.setTranslateX(-100); // Start position to the left (adjust as needed)

        // Fade transition to gradually make the pane visible
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), load);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        // Scale transition to make the pane "zoom in"
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), load);
        scaleTransition.setFromX(0.8);
        scaleTransition.setFromY(0.8);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        // Translate transition to slide the pane in from left to right
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), load);
        translateTransition.setFromX(-100); // Adjust for the starting left position
        translateTransition.setToX(0); // End at the original position

        // Parallel transition to play all effects together
        ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, scaleTransition, translateTransition);
        parallelTransition.play();
    }


}
