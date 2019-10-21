import memory.IncomingBatchHandler;
import requests.KeyValuePair;
import memory.SSCollection;
import requests.IncomingRequest;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import requests.RequestType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class);
    private static final int PORT = 6666;

    private ServerSocket serverSocket;
    private final SSCollection ssCollection;
    private final IncomingBatchHandler incomingBatchHandler;

    Server() {
        ssCollection = new SSCollection();
        incomingBatchHandler = new IncomingBatchHandler(ssCollection);
    }

    void start() throws IOException {
        serverSocket = new ServerSocket(PORT);
        LOGGER.info("Starting Server Socket at port " + PORT);
        while (true)
            new ClientHandler(serverSocket.accept(), incomingBatchHandler).start();
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

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private final IncomingBatchHandler incomingBatchHandler;


        ClientHandler(Socket socket, IncomingBatchHandler incomingBatchHandler) {
            this.clientSocket = socket;
            this.incomingBatchHandler = incomingBatchHandler;
            LOGGER.info("Starting new Client Handler");
        }

        @Override
        public void run() {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                InputStream in = clientSocket.getInputStream();
                byte[] rawBytes = in.readAllBytes();

                IncomingRequest incomingRequest = SerializationUtils.deserialize(rawBytes);

                if (incomingRequest.getRequestType().equals(RequestType.SET.toString())) {

                    KeyValuePair keyValuePair = SerializationUtils.deserialize(incomingRequest.getPayload());
                    incomingBatchHandler.set(keyValuePair.getKey(), keyValuePair.getValue());

                } else if (incomingRequest.getRequestType().equals(RequestType.GET.toString())) {
                    //
                }

                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException e) {
                LOGGER.error("Encountered IO Exception", e);
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
