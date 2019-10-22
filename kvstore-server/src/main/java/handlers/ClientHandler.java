package handlers;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import requests.GetRequest;
import requests.IncomingRequest;
import requests.SetRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            InputStream in = clientSocket.getInputStream();
            byte[] rawBytes = in.readAllBytes();

            IncomingRequest incomingRequest = SerializationUtils.deserialize(rawBytes);

            switch (incomingRequest.getRequestType()) {
                case "SET":
                    LOGGER.info("Received set request");
                    SetRequest setRequest = SerializationUtils.deserialize(incomingRequest.getPayload());
                    handler.set(setRequest.getKey(), setRequest.getValue());
                    out.write("Added key value pair");
                    break;
                case "GET":
                    LOGGER.info("Received get request");
                    GetRequest getRequest = SerializationUtils.deserialize(incomingRequest.getPayload());
                    byte[] value = handler.get(getRequest.getKey());
                    out.write(new String(value)); //Send back output in string
                    break;
                default:
            }

            in.close();
            out.close();
            clientSocket.close();

        } catch (IOException e) {
            LOGGER.error("Encountered IO Exception", e);
        }
    }
}
