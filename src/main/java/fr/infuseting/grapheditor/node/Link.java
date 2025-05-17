package fr.infuseting.grapheditor.node;

import fr.infuseting.grapheditor.node.port.Port;

public class Link {
    private final String id;
    private Port sourcePort;
    private Port targetPort;
    private boolean selected;


    public Link(String id, Port sourcePort, Port targetPort) {
        this.id = id;
        this.sourcePort = sourcePort;
        this.targetPort = targetPort;
    }

    public boolean unselected() {
        if (sourcePort == null || targetPort == null) {
            return false;
        }
        return true;
    }

}
