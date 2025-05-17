package fr.infuseting.grapheditor.util;

import fr.infuseting.grapheditor.node.Node;

import java.util.ArrayList;
import java.util.List;
public class NodeEntry {
    private final String label;
    private final Class type;
    private final List<NodeEntry> children = new ArrayList<>();

    public NodeEntry(String label, Class type) {
        this.label = label;
        this.type = type;
    }

    public void addChild(NodeEntry child) {
        children.add(child);
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public String getLabel() {
        return label;
    }

    public Class getType() {
        return type;
    }

    public List<NodeEntry> getChildren() {
        return children;
    }
}
