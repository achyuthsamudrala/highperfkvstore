package memory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SSCollection {

    private final List<SortedSegment> sortedSegments;

    public SSCollection() {
        sortedSegments = new ArrayList<SortedSegment>();
    }

    public byte[] get(byte[] key) {
        return null;
    }

    public boolean addSegment(SortedSegment sortedSegment) {
        return sortedSegments.add(sortedSegment);
    }

    public Iterator iterator() {
        return new SSCollectionIterator();
    }

    private class SSCollectionIterator implements Iterator {

        public boolean hasNext() {
            return false;
        }

        public Object next() {
            return null;
        }
    }
}
