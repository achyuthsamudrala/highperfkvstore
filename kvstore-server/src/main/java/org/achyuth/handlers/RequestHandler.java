package org.achyuth.handlers;

public interface RequestHandler {

    String get(String key) throws Exception;
    void set(String key, String value) throws Exception;
    void delete(String key) throws Exception;
}
