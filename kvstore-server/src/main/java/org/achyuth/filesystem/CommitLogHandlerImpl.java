package org.achyuth.filesystem;

public class CommitLogHandlerImpl implements CommitLogHandler {

    private final FsUtils fsUtils;

    public CommitLogHandlerImpl(FsUtils fsUtils) {
        this.fsUtils = fsUtils;
    }

    public boolean append(String key, String value) {
        return false;
    }
}
