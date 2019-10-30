package org.achyuth.filesystem;

import org.achyuth.memory.SSCollection;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.util.concurrent.*;

public class LocalFileSystemWriter implements FsWriter {

    private static final Logger LOGGER = Logger.getLogger(LocalFileSystemWriter.class);
    private static final Duration INITIAL_DELAY = Duration.ofMinutes(5);
    private static final Duration INTERVAL_BETWEEN_EXECUTION = Duration.ofMinutes(30);

    private final ScheduledExecutorService executorService;

    LocalFileSystemWriter() {
        executorService =  Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void write(SSCollection ssCollection)  {
        executorService.scheduleWithFixedDelay(new FilesystemPersister(ssCollection),
            INITIAL_DELAY.toMillis(), INTERVAL_BETWEEN_EXECUTION.toMillis(), TimeUnit.MILLISECONDS);
    }


    private class FilesystemPersister implements Runnable {
        private final SSCollection ssCollection;

        FilesystemPersister(SSCollection ssCollection) {
            this.ssCollection = ssCollection;
        }
        @Override
        public void run() {
            SSCollection ssCollectionCopy = ssCollection.shallowCopy();
            LOGGER.info("Persisting SSCollection to Disk");
        }
    }

}
