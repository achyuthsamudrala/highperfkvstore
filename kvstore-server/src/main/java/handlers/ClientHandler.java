package handlers;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import requests.IncomingRequest;
import requests.KeyValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


public class ClientHandler extends Thread {

    private static final Logger LOGGER = Logger.getLogger(ClientHandler.class);
    private Socket clientSocket;
    private final RequestHandler handler;

    public ClientHandler(Socket socket, RequestHandler requestHandler) {
        this.clientSocket = socket;
        this.handler = requestHandler;
        LOGGER.info("Starting new Client Handler");
    }

    @Override
    public void run() {
        try {
            //PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            InputStream in = clientSocket.getInputStream();
            byte[] rawBytes = in.readAllBytes();


            IncomingRequest incomingRequest = SerializationUtils.deserialize(rawBytes);

            switch (incomingRequest.getRequestType()) {
                case "SET":
                    LOGGER.info("Received set request");
                    KeyValuePair keyValuePair = SerializationUtils.deserialize(incomingRequest.getPayload());
                    handler.set(keyValuePair.getKey(), keyValuePair.getValue());
                    LOGGER.info("Adding key value to store");
                    break;
                case "GET":
                    LOGGER.info("Received get request");
                    break;
                default:
            }

            in.close();
            //out.close();
            clientSocket.close();

        } catch (IOException e) {
            LOGGER.error("Encountered IO Exception", e);
        }
    }
}
