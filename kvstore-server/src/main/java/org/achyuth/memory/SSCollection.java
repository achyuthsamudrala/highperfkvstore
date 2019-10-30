package org.achyuth.memory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SSCollection {

    private static AtomicInteger SEGMENT_COUNTER= new AtomicInteger(1);
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
    public synchronized boolean addSegment(Map<byte[], byte[]> keyValues) {
        int increasedSegmentCounter = SEGMENT_COUNTER.addAndGet(1);
        return sortedSegments.add(new SortedSegment(keyValues, increasedSegmentCounter));
    }

    public Iterator iterator() {
        return sortedSegments.iterator();
    }

    public SSCollection shallowCopy() {
        return new SSCollection(sortedSegments);
    }

    public int size() {
        return sortedSegments.size();
    }
}
