package fr.infuseting.grapheditor.controller;

import fr.infuseting.grapheditor.node.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class CanvasController implements Initializable {

    @FXML public Pane canvasContainer;
    @FXML public ScrollPane scrollPane;
    @FXML public Group group;
    @FXML public Pane pane;
    public Controller controller;
    public static int idNode = 0;
    public static int idLink = 0;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        canvasContainer.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            canvasContainer.setPrefHeight(newValue.getHeight());
            canvasContainer.setPrefWidth(newValue.getWidth());
        });

        scrollPane.prefHeightProperty().bind(canvasContainer.heightProperty());
        scrollPane.prefWidthProperty().bind(canvasContainer.widthProperty());

        scrollPane.viewportBoundsProperty().addListener((obs, oldVal, newVal) -> {
            pane.setPrefWidth(newVal.getWidth());
            pane.setPrefHeight(newVal.getHeight());
        });

    }

    @FXML
    private void onMouseMoved(MouseEvent mouseEvent) {
        controller.prevX.set(mouseEvent.getX());
        controller.prevY.set(mouseEvent.getY());
    }


    public void createNewNode(String label, double x, double y) {
        Class<? extends fr.infuseting.grapheditor.node.Node> classNode = fr.infuseting.grapheditor.node.Node.getRegister().stream()
                .filter(c -> c.getSimpleName().equals(label))
                .map(c -> (Class<? extends fr.infuseting.grapheditor.node.Node>) c)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such node type: " + label));
        fr.infuseting.grapheditor.node.Node instance;
        try {
            instance = classNode.getConstructor(int.class, double.class, double.class)
                    .newInstance(idNode, x, y);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        idNode++;
        instance.addInCanvas(pane, controller);
    }

    public Node getGroup() {
        return group;
    }
}
