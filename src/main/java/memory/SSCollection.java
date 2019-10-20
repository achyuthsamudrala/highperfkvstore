package memory;

import java.util.ArrayList;
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
}
