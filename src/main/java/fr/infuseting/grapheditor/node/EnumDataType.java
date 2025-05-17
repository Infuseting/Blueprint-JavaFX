package fr.infuseting.grapheditor.node;

import java.awt.*;

public enum EnumDataType {
    INT("int", Color.RED),
    STRING("string", Color.YELLOW),
    BOOL("bool", Color.BLUE),
    DOUBLE("double", Color.CYAN),
    MONSTER("monster", Color.GREEN),
    PLACE("place", Color.pink),
    PATHS("paths", Color.GRAY);

    private final String name;
    private final Color color;

    EnumDataType(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name;
    }
}
