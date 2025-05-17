package fr.infuseting.grapheditor.node.port;

import fr.infuseting.grapheditor.node.EnumDataType;
import fr.infuseting.grapheditor.node.Link;
import fr.infuseting.grapheditor.node.Node;

import java.awt.*;

public class Port {
    protected int id;
    protected String name;
    protected EnumPortType type;
    protected EnumDataType dataType;
    protected Node parentNode;
    protected Link link;
    protected final boolean mandatory;
    protected final Color color;

    public Port(int id, String name, EnumPortType type, EnumDataType dataType, Node parentNode, boolean mandatory) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.dataType = dataType;
        this.parentNode = parentNode;
        this.mandatory = mandatory;
        this.color = dataType.getColor();
    }

    public void addLink(Link link) {
        this.link = link;
    }
    public void removeLink() {
        this.link = null;
    }
    public Link getLink() {
        return link;
    }
    public String getName() {
        return name;
    }
    public EnumPortType getPortType() {
        return type;
    }


    public Color getColor() {
        return this.color;
    }
}

