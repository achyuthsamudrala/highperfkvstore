package org.achyuth;

import org.achyuth.filesystem.*;
import org.achyuth.handlers.ClientHandler;
import org.achyuth.handlers.RequestHandlerImpl;
import org.achyuth.handlers.RequestHandler;
import org.achyuth.memory.MemoryCollection;

import org.apache.log4j.Logger;


import java.io.*;
import java.net.ServerSocket;


public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class);
    private static final int PORT = 6666;
    private static final int BATCH_SIZE = 10000;

    private ServerSocket serverSocket;
    private final MemoryCollection ssCollection;
    private final RequestHandler requestHandler;

    Server() {
        ssCollection = new MemoryCollection(BATCH_SIZE);
         ;
        CommitLogHandler commitLogHandler = new CommitLogHandlerImpl(FsUtilsFactory.getWriter("local"));
        StoredDataHandler storedDataHandler = new StoredDataHandlerImpl(FsUtilsFactory.getWriter("local"));
        requestHandler = new RequestHandlerImpl(ssCollection, commitLogHandler, storedDataHandler);
    }

    void start() throws IOException {
        serverSocket = new ServerSocket(PORT);
        LOGGER.info("Starting org.achyuth.Server Socket at port " + PORT);
        while (true)
            new ClientHandler(serverSocket.accept(), requestHandler).start();
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
