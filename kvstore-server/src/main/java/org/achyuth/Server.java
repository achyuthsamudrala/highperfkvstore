package org.achyuth;

import org.achyuth.filesystem.FsWriter;
import org.achyuth.filesystem.FsWriterFactory;
import org.achyuth.handlers.ClientHandler;
import org.achyuth.handlers.BatchingRequestHandler;
import org.achyuth.handlers.RequestHandler;
import org.achyuth.memory.SSCollection;

import org.apache.log4j.Logger;


import java.io.*;
import java.net.ServerSocket;


public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class);
    private static final int PORT = 6666;
    private static final int BATCH_SIZE = 10000;

    private ServerSocket serverSocket;
    private final SSCollection ssCollection;
    private final RequestHandler requestHandler;

    Server() {
        ssCollection = new SSCollection();
        requestHandler = new BatchingRequestHandler(ssCollection, BATCH_SIZE);
    }

    void start() throws IOException {
        serverSocket = new ServerSocket(PORT);
        LOGGER.info("Starting org.achyuth.Server Socket at port " + PORT);
        persistSSCollection();
        while (true)
            new ClientHandler(serverSocket.accept(), requestHandler).start();
    }

    void persistSSCollection() {
        FsWriter fsWriter = FsWriterFactory.getWriter("local");
        if (fsWriter != null)
            fsWriter.write(ssCollection);
        else LOGGER.error("FsWriter is NULL");
    }

    void stop() {
        if (serverSocket != null) {
            try {
                LOGGER.info("Stopping KV Store server");
                serverSocket.close();
            } catch (IOException e) {
                LOGGER.error("Error encountered while trying to stop the server", e);
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            LOGGER.info("Starting KV Store server");
            server.start();
        } catch (IOException e) {
            LOGGER.error("Encountered exception when starting the server", e);
        } finally {
            server.stop();
        }
    }
}
