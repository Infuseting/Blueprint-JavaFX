package fr.infuseting.grapheditor.controller;

import fr.infuseting.grapheditor.node.Node;
import fr.infuseting.grapheditor.util.NodeEntry;
import fr.infuseting.grapheditor.util.NodeEntryFactory;
import fr.infuseting.grapheditor.util.NodeEntryFilter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;



public class NodeCreatorController implements Initializable{
    public Controller controller;
    public Double X;
    public Double Y;
    @FXML public TextField searchField;
    @FXML public VBox nodeContainer;
    public void setController(Controller controller) {
        this.controller = controller;
    }
    private void loadNodeEntries() {
        List<NodeEntry> rootEntries = NodeEntryFactory.build(); // <-- ici
        updateNodeContainer(rootEntries);
    }

    private void updateFilteredList(String query) {
        List<NodeEntry> rootEntries = NodeEntryFactory.build();
        List<NodeEntry> filtered = NodeEntryFilter.filter(rootEntries, query);
        updateNodeContainer(filtered);
    }

    private void updateNodeContainer(List<NodeEntry> entries) {
        nodeContainer.getChildren().clear();
        for (NodeEntry entry : entries) {
            javafx.scene.Node fxNode = buildNodeSelector(entry);
            nodeContainer.getChildren().add(fxNode);
        }
    }

    private javafx.scene.Node buildNodeSelector(NodeEntry entry) {
        if (entry.isLeaf()) {
            Button btn = new Button(entry.getLabel());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setOnAction(e -> {controller.canvasController.createNewNode(entry.getLabel(), X, Y);  ((Stage) btn.getScene().getWindow()).close();}); // Adjusted to use getLabel

            return btn;
        } else {
            VBox content = new VBox(5);
            for (NodeEntry child : entry.getChildren()) {
                content.getChildren().add(buildNodeSelector(child));
            }
            TitledPane pane = new TitledPane(entry.getLabel(), content);
            pane.setExpanded(false);
            return pane;
        }
    }

    public void setCoords(Double x, Double y) {
        this.X = x;
        this.Y = y;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            updateFilteredList(newVal.toLowerCase());
        });
        loadNodeEntries();
    }
}