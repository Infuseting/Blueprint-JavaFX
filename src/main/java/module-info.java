module fr.infuseting.grapheditor {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens fr.infuseting.grapheditor to javafx.fxml;
    exports fr.infuseting.grapheditor;
    exports fr.infuseting.grapheditor.controller;
    opens fr.infuseting.grapheditor.controller to javafx.fxml;
}