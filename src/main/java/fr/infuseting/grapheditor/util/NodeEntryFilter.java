package fr.infuseting.grapheditor.util;

import java.util.*;
import java.util.stream.Collectors;

public class NodeEntryFilter {

    // Filtre récursif : retourne la liste filtrée des NodeEntry dont le label contient query
    public static List<NodeEntry> filter(List<NodeEntry> entries, String query) {
        if (query == null || query.isEmpty()) {
            return entries;
        }

        List<NodeEntry> filtered = new ArrayList<>();
        for (NodeEntry entry : entries) {
            NodeEntry filteredEntry = filterEntry(entry, query.toLowerCase());
            if (filteredEntry != null) {
                filtered.add(filteredEntry);
            }
        }
        return filtered;
    }

    private static NodeEntry filterEntry(NodeEntry entry, String query) {
        boolean matches = entry.getLabel().toLowerCase().contains(query);

        List<NodeEntry> filteredChildren = entry.getChildren().stream()
                .map(child -> filterEntry(child, query))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (matches || !filteredChildren.isEmpty()) {
            NodeEntry copy = new NodeEntry(entry.getLabel(), entry.getType());
            filteredChildren.forEach(copy::addChild);
            return copy;
        }
        return null;
    }
}
