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
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.util.List;

public class StringNode extends Node{
    private String value;
    public StringNode(int id, double x, double y) {
        this(id, "", x, y);
    }
    public StringNode(int id, String value, double x, double y) {
        this(id, value, x, y, 200, 100);
    }
    public StringNode(int id, String value, double x, double y, double width, double height) {
        super(id, "StringNode", x, y, width, height);
        this.value = value;
        addPort(EnumPortType.OUTPUT, new Port(0, "StringOutput", EnumPortType.OUTPUT, EnumDataType.STRING, this, false));
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
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
            nodeView.setOnMouseDragged(event -> {
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
                input.setPromptText("String");
                input.setText(String.valueOf(this.value));

                input.focusedProperty().addListener((event -> {
                    if (!input.isFocused()) {
                        this.value = input.getText().toString();


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
