package org.achyuth.memory;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SSCollection {

    private final List<SortedSegment> sortedSegments;

    public SSCollection() {
        sortedSegments = new ArrayList<>();
    }

    private SSCollection(List<SortedSegment> sortedSegments) {
        this.sortedSegments = sortedSegments;
    }

    public byte[] get(byte[] key) {
        for (int i=size()-1;i>=0;i--) {
            SortedSegment currentSegment = sortedSegments.get(i);
            if(currentSegment.containsKey(key))
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

        public Pair<byte[], byte[]> next() {
            return null;
        }
    }

    public SSCollection shallowCopy() {
        return new SSCollection(sortedSegments);
    }

    public int size() {
        return sortedSegments.size();
    }
}
