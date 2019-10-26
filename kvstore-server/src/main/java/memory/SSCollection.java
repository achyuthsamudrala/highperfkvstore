package memory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SSCollection {

    private final List<SortedSegment> sortedSegments;

    public SSCollection() {
        sortedSegments = new ArrayList<>();
    }

    public byte[] get(byte[] key) {
        for (int i=size()-1;i>=0;i--) {
            SortedSegment currentSegment = sortedSegments.get(i);
            if(currentSegment.containKey(key))
                return currentSegment.get(key);
        }
        return null;
    }

    /* Multiple threads could be writing to this at the same time */
    public synchronized boolean addSegment(SortedSegment sortedSegment) {
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

    public int size() {
        return sortedSegments.size();
    }
}
