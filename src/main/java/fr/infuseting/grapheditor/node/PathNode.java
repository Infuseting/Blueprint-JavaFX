package fr.infuseting.grapheditor.node;

import fr.infuseting.grapheditor.App;
import fr.infuseting.grapheditor.controller.Controller;
import fr.infuseting.grapheditor.node.port.EnumPortType;
import fr.infuseting.grapheditor.node.port.Port;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

public class PathNode extends Node{

    public PathNode(int id, double x, double y) {
        this(id, x, y, 200, 180);
    }
    public PathNode(int id, double x, double y, double width, double height) {
        super(id, "PathNode", x, y, width, height);
        addPort(EnumPortType.INPUT, new Port(0, "PlaceFrom", EnumPortType.INPUT, EnumDataType.PLACE, this, true));
        addPort(EnumPortType.INPUT, new Port(0, "PlaceTo", EnumPortType.INPUT, EnumDataType.PLACE, this, true));
        addPort(EnumPortType.INPUT, new Port(0, "Distance", EnumPortType.INPUT, EnumDataType.INT, this, true));

    }
    @Override
    public void addInCanvas(Pane canva, Controller controller) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("node.fxml"));
            BorderPane nodeView = loader.load();
            Label nodeName = (Label) nodeView.lookup("#NodeName");
            if (nodeName != null) {
                nodeName.setText(this.getClass().getSimpleName());
            }
            canva.getChildren().add(nodeView);
            nodeView.setLayoutX(this.x);
            nodeView.setLayoutY(this.y);
            nodeView.setPrefWidth(this.width);
            nodeView.setPrefHeight(this.height);
            nodeView.setOnMouseReleased(event-> { controller.nodeProperty.setValue("null");});
            nodeView.setOnMouseDragged(event -> {
                controller.nodeProperty.setValue(this.name);
                this.x = this.x + event.getX();
                this.y = this.y + event.getY();

                nodeView.setLayoutX(this.x);
                nodeView.setLayoutY(this.y);
                event.consume();
            });
            List<Port> portsInput = getPorts(EnumPortType.INPUT);
            List<Port> portsOutput = getPorts(EnumPortType.OUTPUT);
            GridPane pane = (GridPane) nodeView.lookup("#inputPortGrid");



            generatePort(portsInput, nodeView, controller, EnumPortType.INPUT);
            generatePort(portsOutput, nodeView, controller, EnumPortType.OUTPUT);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
