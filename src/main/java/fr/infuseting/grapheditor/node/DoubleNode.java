package fr.infuseting.grapheditor.node;

import fr.infuseting.grapheditor.App;
import fr.infuseting.grapheditor.controller.Controller;
import fr.infuseting.grapheditor.node.port.EnumPortType;
import fr.infuseting.grapheditor.node.port.Port;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;

public class DoubleNode extends Node{
    private double value;

    public DoubleNode(int id, double x, double y) {
        this(id, 0.0, x, y);
    }
    public DoubleNode(int id, double value, double x, double y) {
        this(id, value, x, y, 200, 100);
    }
    public DoubleNode(int id, double value, double x, double y, double width, double height) {
        super(id, "DoubleNode", x, y, width, height);
        this.value = value;
        addPort(EnumPortType.OUTPUT, new Port(0, "DoubleOutput", EnumPortType.OUTPUT, EnumDataType.DOUBLE, this, false));
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
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

            if (portsInput.isEmpty()) {
                pane.addRow(1);
                TextField input = new TextField();
                input.setPromptText("Double");
                input.setText(String.valueOf(this.value));

                input.focusedProperty().addListener((event -> {
                    if (!input.isFocused()) {
                        if (input.getText().matches("-?\\d+(\\.\\d+)?")) {
                            this.value = Double.parseDouble(input.getText().strip());
                        } else {

                            controller.errorPopup("Invalid value", "Please enter a valid Double value (<int>.<int>)", "Error");
                            input.setText(String.valueOf(this.value));
                            input.requestFocus();
                        }

                    }

                }));
                pane.add(input, 0, 1);
                input.requestFocus();



            }
            generatePort(portsInput, nodeView, controller, EnumPortType.INPUT);
            generatePort(portsOutput, nodeView, controller, EnumPortType.OUTPUT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
