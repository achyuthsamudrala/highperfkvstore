package org.achyuth.memory;

import org.achyuth.common.ByteArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import java.util.*;

public class SortedSegment {

    private final List<Pair<byte[], byte[]>> keyValues;

    public SortedSegment(final Map<byte[], byte[]> inputMap) {
        final Map<byte[], byte[]> sortedMap = new TreeMap<>(inputMap);
        keyValues = new ArrayList<>();
        for (Map.Entry<byte[], byte[]> entry: sortedMap.entrySet()) {
            keyValues.add(Pair.of(entry.getKey(), entry.getValue()));
        }
    }

    byte[] get(byte[] key) {
        return binarySearch(key);
    }

    public Iterator iterator() {
        return keyValues.iterator();
    }

    boolean containsKey(byte[] key) {
        return binarySearch(key) != null;
    }

    private byte[] binarySearch(byte[] key) {
        int low = 0;
        int high = keyValues.size()-1;

        while (low <= high) {
            final int mid = (low + high) >>> 1;
            final byte[] midVal = keyValues.get(mid).getKey();
            final int cmp = ByteArrayUtils.compareByteArrays(midVal, key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return keyValues.get(mid).getValue();
        }
        return null;
    }

}

