package fr.infuseting.grapheditor.controller;

import fr.infuseting.grapheditor.App;
import fr.infuseting.grapheditor.Dialogues;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML public CanvasController canvasController;
    @FXML public MenusController menusController;
    @FXML public StatutController statutController;
    public NodeCreatorController nodeCreatorController;

    public final SimpleDoubleProperty prevX = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty prevY = new SimpleDoubleProperty(0);

    public final SimpleStringProperty titleProperty = new SimpleStringProperty("GraphEditor - New Graph");
    public final SimpleStringProperty nodeProperty = new SimpleStringProperty("Null");




    public boolean onQuitter() {
        if (Dialogues.confirmation()) {
            return true;
        }
        return false;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menusController.setController(this);
        canvasController.setController(this);
        statutController.setController(this);


        statutController.coordsXLabel.textProperty().bind(prevX.asString("%.0f"));
        statutController.coordsYLabel.textProperty().bind(prevY.asString("%.0f"));
        statutController.nodeLabel.textProperty().bind(nodeProperty);
        statutController.zoomLabel.textProperty().bind(canvasController.getGroup().scaleXProperty().asString("%.2f"));

        canvasController.pane.setOnMouseMoved(event -> {
            prevX.set(event.getX());
            prevY.set(event.getY());
        });
    }

    public SimpleStringProperty getTitleProperty() {
        return titleProperty;
    }
    public SimpleStringProperty changeTitle(String title) {
        titleProperty.set("GraphEditor - " + title);
        return titleProperty;
    }

    public void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.T && event.isControlDown()) {

            openNodeCreationWindow(prevX.getValue(), prevY.getValue());
        }
    }
    public void onMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            openNodeCreationWindow(prevX.getValue(), prevY.getValue());
        }
    }

    private void openNodeCreationWindow(Double X, Double Y) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("nodeCreator.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            NodeCreatorController nodeCreatorController = fxmlLoader.getController();
            nodeCreatorController.setController(this);
            nodeCreatorController.setCoords(X, Y);
            this.nodeCreatorController = nodeCreatorController;
            stage.setTitle("Node Creation");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void errorPopup(String invalidValue, String s, String error) {
        Dialogues.errorPopup(invalidValue, s, error);
    }
}