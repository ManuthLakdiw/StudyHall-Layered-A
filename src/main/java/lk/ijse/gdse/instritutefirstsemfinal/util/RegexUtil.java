package lk.ijse.gdse.instritutefirstsemfinal.util;

import javafx.animation.TranslateTransition;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class RegexUtil {

    public static void shake(TextField... fields) {
        for (TextField field : fields) {
            TranslateTransition shake = new TranslateTransition(Duration.millis(50), field);
            shake.setFromX(0);
            shake.setByX(4);
            shake.setCycleCount(6);
            shake.setAutoReverse(true);
            shake.play();
        }
    }

    public static void setErrorStyle(boolean skakeEffect , TextField... fields) {
        String errorStyle = "-fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;";
        for (TextField field : fields) {
            field.setStyle(errorStyle);
            if (skakeEffect) {
                shake(field);
            }

        }
    }

    public static void resetStyle(TextField... fields) {
        String resetStyle = "-fx-border-color: #03045E; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;";
        for (TextField field : fields) {
            field.setStyle(resetStyle);
        }

    }

    public static<T>void selectedStyle(T... component) {
        String selectedStyle = "-fx-border-color: #03045E; -fx-border-width: 1.5px; -fx-border-radius: 5; -fx-background-color: transparent;";
        for (T t : component) {
            if (t instanceof TextField) {
                ((TextField) t).setStyle(selectedStyle);
            } else if (t instanceof Label) {
                ((Label) t).setStyle(selectedStyle);
            }
        }
    }
}
