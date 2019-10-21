package memory;

import java.util.Iterator;
import java.util.Map;

public class SortedSegment {

    private final Map<byte[], byte[]> keyValues;

    public SortedSegment(final Map<byte[], byte[]> sortedSegmentMap) {
        keyValues = sortedSegmentMap;
    }

    public Map<byte[], byte[]> getKeyValues() {
        return keyValues;
    }

    public byte[] get(byte[] key) {
        return null;
    }

    public Iterator iterator() {
        return new SortedSegmentIterator();
    }

    public SSCollection getSnapshot() {
        return null;
    }

    private class SortedSegmentIterator implements Iterator {

        public boolean hasNext() {
            return false;
        }

        public Object next() {
            return null;
        }
    }
}
