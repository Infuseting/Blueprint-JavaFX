package fr.infuseting.grapheditor.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StatutController  implements Initializable {

    private Controller controller;
    @FXML public Label coordsXLabel;
    @FXML public Label coordsYLabel;
    @FXML public Label nodeLabel;
    @FXML public Label zoomLabel;
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the canvas here if needed
    }
}
