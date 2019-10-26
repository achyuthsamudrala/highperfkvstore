package memory;

import org.apache.commons.lang3.tuple.Pair;
import java.util.*;

public class SortedSegment {

    private final List<Pair<byte[], byte[]>> keyValues;

    public SortedSegment(final Map<byte[], byte[]> inputMap) {
        Map<byte[], byte[]> sortedMap = new TreeMap<>(inputMap);
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

    public SSCollection getSnapshot() {
        return null;
    }

    boolean containsKey(byte[] key) {
        return binarySearch(key) != null;
    }

    private byte[] binarySearch(byte[] key) {
        int low = 0;
        int high = keyValues.size()-1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            byte[] midVal = keyValues.get(mid).getKey();
            int cmp = compareByteArrays(midVal, key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return keyValues.get(mid).getValue();
        }
        return null;
    }

    /* return 1 if byteArray1 > byteArray2, 0 if byteArray1 == byteArray2, -1 if byteArray1 < byteArray2
     */
    int compareByteArrays(byte[] byteArray1, byte[] byteArray2 ) {
        if ( byteArray1 == byteArray2 )
            return 0;

        if ( byteArray1 == null )
            return -1;
        else {
            if ( byteArray2 == null ) {
                return 1;
            } else {
                if ( byteArray1.length < byteArray2.length ) {
                    int pos = 0;
                    for (byte b1 : byteArray1) {
                        byte b2 = byteArray2[pos];
                        if ( b1 == b2 )
                            pos++;
                        else if ( b1 < b2 )
                            return -1;
                        else
                            return 1;
                    }
                    return -1;
                } else {
                    int pos = 0;
                    for (byte b2 : byteArray2) {
                        byte b1 = byteArray1[pos];
                        if ( b1 == b2 )
                            pos++;
                        else if ( b1 < b2 )
                            return -1;
                        else
                            return 1;
                    }
                    if ( pos < byteArray1.length )
                        return 1;
                    else
                        return 0;
                }
            }
        }
    }
}

