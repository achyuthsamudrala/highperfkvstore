package org.achyuth.exceptions;

public class CommitLogAppendFailedException extends Exception {
    public CommitLogAppendFailedException(String errorMessage) {
        super(errorMessage);
    }
}
