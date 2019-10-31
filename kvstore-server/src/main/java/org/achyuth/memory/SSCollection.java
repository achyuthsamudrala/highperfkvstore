package org.achyuth.memory;

import org.achyuth.exceptions.FsWriterUnavailableException;
import org.achyuth.exceptions.KeyNotFoundException;
import org.achyuth.filesystem.FsWriter;
import org.achyuth.filesystem.FsWriterFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SSCollection {

    private static final int CAPACITY = 100;
    private static AtomicInteger SEGMENT_COUNTER= new AtomicInteger(1);
    private final List<SortedSegment> sortedSegments;
    private static final Logger LOGGER = Logger.getLogger(SSCollection.class);


    public SSCollection() {
        sortedSegments = new ArrayList<>(CAPACITY);
    }

    private SSCollection(List<SortedSegment> sortedSegments) {
        this.sortedSegments = sortedSegments;
    }

    public String get(String key) throws KeyNotFoundException {
        for (int i=size()-1;i>=0;i--) {
            SortedSegment currentSegment = sortedSegments.get(i);
            if(currentSegment.containsKey(key))
                return currentSegment.get(key);
        }
        throw new KeyNotFoundException("Key not found in collection");
    }

    /* Multiple threads could be writing to this at the same time */
    public synchronized boolean addSegment(Map<String, String> keyValues) throws Exception {
        if (sortedSegments.size() >= CAPACITY) {
            persistWhenFull();
            sortedSegments.clear();
        }
        int increasedSegmentCounter = SEGMENT_COUNTER.addAndGet(1);
        return sortedSegments.add(new SortedSegment(keyValues, increasedSegmentCounter));
    }

    private void persistWhenFull() throws FsWriterUnavailableException {
        FsWriter fsWriter = FsWriterFactory.getWriter("local");
        if (fsWriter != null)
            fsWriter.write(shallowCopy());
        else throw new FsWriterUnavailableException("Unable to persist to disk, FSWriter is Null");
    }

    public Iterator iterator() {
        return sortedSegments.iterator();
    }

    SSCollection shallowCopy() {
        return new SSCollection(sortedSegments);
    }

    public int size() {
        return sortedSegments.size();
    }
}
