package fr.infuseting.grapheditor.util;

import fr.infuseting.grapheditor.node.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.*;
public class NodeEntryFactory {

    public static List<NodeEntry> build() {
        Map<Class, List<Class>> map = new HashMap<>();

        // Ajout des racines (ici, Node est un root)
        map.computeIfAbsent(null, k -> new ArrayList<>()).add(Node.class);

        // Ajout des enfants de Node
        map.computeIfAbsent(Node.class, k -> new ArrayList<>()).add(BoolNode.class);
        map.get(Node.class).add(DoubleNode.class);
        map.get(Node.class).add(IntNode.class);
        map.get(Node.class).add(MonsterNode.class);
        map.get(Node.class).add(PathNode.class);
        map.get(Node.class).add(PlaceNode.class);
        map.get(Node.class).add(StringNode.class);

        return buildTreeFromMap(map, null); // ⬅ utiliser null pour les racines
    }

    private static List<NodeEntry> buildTreeFromMap(Map<Class, List<Class>> hierarchyMap, Class rootType) {
        List<NodeEntry> roots = new ArrayList<>();
        List<Class> children = hierarchyMap.get(rootType);
        if (children != null) {
            for (Class child : children) {
                roots.add(buildNodeEntry(child, hierarchyMap));
            }
        }
        return roots;
    }

    private static NodeEntry buildNodeEntry(Class type, Map<Class, List<Class>> hierarchyMap) {
        NodeEntry entry = new NodeEntry(type.getSimpleName(), type);
        List<Class> children = hierarchyMap.get(type);
        if (children != null) {
            for (Class child : children) {
                entry.addChild(buildNodeEntry(child, hierarchyMap));
            }
        }
        return entry;
    }
}
