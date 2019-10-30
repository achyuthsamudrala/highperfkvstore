package org.achyuth.memory;

import org.apache.commons.lang3.tuple.Pair;
import java.util.*;

public class SortedSegment {

    private final int uniqueId;
    private final List<Pair<String, String>> keyValues;

    public SortedSegment(final Map<String, String> inputMap, int uniqueId) {
        this.uniqueId = uniqueId;
        final Map<String, String> sortedMap = new TreeMap<>(inputMap);
        keyValues = new ArrayList<>();
        for (Map.Entry<String, String> entry: sortedMap.entrySet()) {
            keyValues.add(Pair.of(entry.getKey(), entry.getValue()));
        }
    }

    String get(String key) {
        return binarySearch(key);
    }

    public Iterator iterator() {
        return keyValues.iterator();
    }

    boolean containsKey(String key) {
        return binarySearch(key) != null;
    }

    private String binarySearch(String key) {
        int low = 0;
        int high = keyValues.size()-1;

        while (low <= high) {
            final int mid = (low + high) >>> 1;
            final String midVal = keyValues.get(mid).getKey();
            final int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return keyValues.get(mid).getValue();
        }
        return null;
    }

    public int getUniqueId() {
        return uniqueId;
    }
}

