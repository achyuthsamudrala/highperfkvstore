import handlers.ClientHandler;
import handlers.BatchingRequestHandler;
import handlers.RequestHandler;
import memory.SSCollection;

import org.apache.log4j.Logger;


import java.io.*;
import java.net.ServerSocket;


public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class);
    private static final int PORT = 6666;

    private ServerSocket serverSocket;
    private final SSCollection ssCollection;
    private final RequestHandler requestHandler;

    Server() {
        ssCollection = new SSCollection();
        requestHandler = new BatchingRequestHandler(ssCollection);
    }

    void start() throws IOException {
        serverSocket = new ServerSocket(PORT);
        LOGGER.info("Starting Server Socket at port " + PORT);
        while (true)
            new ClientHandler(serverSocket.accept(), requestHandler).start();
    }

    void stop() {
        if (serverSocket != null) {
            try {
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
