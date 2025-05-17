package fr.infuseting.grapheditor.node;

import fr.infuseting.grapheditor.App;
import fr.infuseting.grapheditor.controller.Controller;
import fr.infuseting.grapheditor.node.port.EnumPortType;
import fr.infuseting.grapheditor.node.port.Port;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import org.controlsfx.control.spreadsheet.Grid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Node<T extends Node<T>> {

    public final int id;
    public final String name;

    public double x, y;
    public double width, height;

    public static final List<Class<? extends Node<?>>> register = new ArrayList<>();

    public boolean selected;

    private final HashMap<EnumPortType, List<Port>> ports = new HashMap<>();

    protected Node(int id, String name, double x, double y) {
        this(id, name, x, y, 200, 100);
    }

    protected Node(int id, String name, double x, double y, double width, double height) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.selected = true;
    }

    protected void addPort(EnumPortType portType, Port port) {
        if (portType != port.getPortType()) {
            throw new IllegalArgumentException("Port type does not match port type");
        }
        if (ports.containsKey(portType)) {
            ports.get(portType).add(port);
        } else {
            ports.put(portType, new ArrayList<>());
            ports.get(portType).add(port);
        }
    }
    public List<Port> getPorts(EnumPortType portType) {
        if (ports.containsKey(portType)) {
            return ports.get(portType);
        } else if (portType == null) {
            return ports.values().stream()
                    .flatMap(List::stream)
                    .toList();
        } else {
            return new ArrayList<>();
        }
    }

    public static List<Class<? extends Node<?>>> getRegister() {
        return register;
    }

    public static void register(Class<? extends Node<?>> node) {
        register.add(node);
    }

    public abstract void addInCanvas(Pane canva, Controller controller);

    public void generatePort(List<Port> portsInput, BorderPane gridPane, Controller controller, EnumPortType portType) {
        GridPane pane = portType == EnumPortType.INPUT ? (GridPane) gridPane.lookup("#inputPortGrid") :  (GridPane) gridPane.lookup("#outputPortGrid");
        double minSize = Double.MAX_VALUE;
        pane.addRow(portsInput.size());
        for (Port port : portsInput) {

            try {
                FXMLLoader loader = new FXMLLoader(App.class.getResource("port.fxml"));
                Pane portView = loader.load();
                pane.add(portView, 0, portsInput.indexOf(port) + 1);
                Pane colorPane = (Pane) portView.lookup("#colorPane");
                Label labelPort = ( Label) portView.lookup("#name");
                GridPane father = (GridPane) portView.getParent();
                father.setColumnIndex(colorPane, portType == EnumPortType.INPUT ? 0 : 1);
                father.setColumnIndex(labelPort, portType == EnumPortType.INPUT ? 1 : 0);
                minSize = Math.min(Math.min(colorPane.getWidth(), colorPane.getHeight()), minSize);
                colorPane.setPrefSize(minSize, minSize);
                colorPane.setMinSize(minSize, minSize);
                colorPane.setMaxSize(minSize, minSize);
                GridPane lookup = (GridPane) father.lookup("#ColorPort");
                lookup.getColumnConstraints().get(EnumPortType.INPUT == portType ? 0 : 1).setMaxWidth(minSize);
                lookup.getColumnConstraints().get(EnumPortType.INPUT == portType ? 0 : 1).setMinWidth(minSize);
                lookup.getColumnConstraints().get(EnumPortType.INPUT == portType ? 0 : 1).setPrefWidth(minSize);

                GridPane.setHalignment(colorPane, portType == EnumPortType.INPUT ? HPos.RIGHT : HPos.LEFT);
                GridPane.setHalignment(labelPort, portType == EnumPortType.INPUT ? HPos.RIGHT : HPos.LEFT);

                GridPane.setHgrow(colorPane, Priority.ALWAYS);

                GridPane.setHgrow(labelPort, Priority.ALWAYS);
                labelPort.setMaxWidth(Double.MAX_VALUE);
                labelPort.setPrefWidth(Double.MAX_VALUE);




                colorPane.setStyle("-fx-background-color: " + toHexColor(port.getColor()));
                labelPort.setText(port.getName());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        for (int i = 0; i < pane.getChildren().size(); i++) {
            if (pane.getChildren().get(i).getId() != null && pane.getChildren().get(i).getId().equalsIgnoreCase("left_port")) {
                Pane child = (Pane) pane.getChildren().get(i);
                if (child != null) {
                    GridPane lookup = (GridPane) child.lookup("#ColorPort");
                    if (lookup != null) {
                        System.out.println("------------------");
                        System.out.println(lookup.getColumnConstraints().get(EnumPortType.INPUT == portType ? 0 : 1).getMinWidth());
                        System.out.println(lookup.getColumnConstraints().get(EnumPortType.INPUT == portType ? 0 : 1).getMaxWidth());
                        System.out.println(lookup.getColumnConstraints().get(EnumPortType.INPUT == portType ? 0 : 1).getPrefWidth());
                        Pane colorPane = (Pane) child.lookup("#colorPane");
                        System.out.println(colorPane.getStyle());
                        lookup.getColumnConstraints().get(EnumPortType.INPUT == portType ? 0 : 1).setMaxWidth(minSize);
                        lookup.getColumnConstraints().get(EnumPortType.INPUT == portType ? 0 : 1).setMinWidth(minSize);
                        lookup.getColumnConstraints().get(EnumPortType.INPUT == portType ? 0 : 1).setPrefWidth(minSize);
                    }
                }
            }

        }


    }
    public String toHexColor(java.awt.Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}