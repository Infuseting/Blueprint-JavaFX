package fr.infuseting.grapheditor;

import fr.infuseting.grapheditor.controller.Controller;
import fr.infuseting.grapheditor.node.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Node.register((Class<? extends Node<?>>) (Class<?>) Node.class);
        Node.register((Class<? extends Node<?>>) (Class<?>) BoolNode.class);
        Node.register((Class<? extends Node<?>>) (Class<?>) IntNode.class);
        Node.register((Class<? extends Node<?>>) (Class<?>) DoubleNode.class);
        Node.register((Class<? extends Node<?>>) (Class<?>) MonsterNode.class);
        Node.register((Class<? extends Node<?>>) (Class<?>) StringNode.class);
        Node.register((Class<? extends Node<?>>) (Class<?>) PlaceNode.class);
        Node.register((Class<? extends Node<?>>) (Class<?>) PathNode.class);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("graph.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        Controller controller = fxmlLoader.getController();
        stage.titleProperty().bind(controller.getTitleProperty());
        stage.setScene(scene);
        stage.getScene().setOnKeyPressed(event -> controller.onKeyPressed(event));
        stage.show();
        closeWindow(stage, controller);
    }

    public void closeWindow(Stage stage, Controller controller) {
        stage.setOnCloseRequest(event -> {
            if (!controller.onQuitter()) {
                event.consume();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}