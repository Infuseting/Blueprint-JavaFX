package fr.infuseting.grapheditor;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Dialogues{
    public static boolean confirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de fermeture");
        alert.setContentText("Êtes-vous sûr de vouloir fermer la fenêtre ?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(ButtonType.NO) == ButtonType.OK;
    }

    public static void errorPopup(String invalidValue, String s, String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(invalidValue);
        alert.setContentText(s);
        alert.showAndWait();
    }
}