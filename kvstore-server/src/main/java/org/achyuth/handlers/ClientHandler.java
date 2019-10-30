package org.achyuth.handlers;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import org.achyuth.requests.GetRequest;
import org.achyuth.requests.IncomingRequest;
import org.achyuth.requests.SetRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import org.apache.commons.codec.binary.Hex;


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
                    try {
                        String keyInHex = Hex.encodeHexString(setRequest.getKey());
                        String keyInValue = Hex.encodeHexString(setRequest.getValue());
                        handler.set(keyInHex, keyInValue);
                        out.write("Added key value pair");
                    } catch (Exception e) {
                        LOGGER.error("Unable to make a set request", e);
                        out.write("Unable to make a set request");
                    }
                    break;
                case "GET":
                    LOGGER.info("Received get request");
                    GetRequest getRequest = SerializationUtils.deserialize(incomingRequest.getPayload());
                    try {
                        String keyInHex = Hex.encodeHexString(getRequest.getKey());
                        String value = handler.get(keyInHex);
                        out.write(value); //Send back output in string
                    } catch (Exception e) {
                        LOGGER.error("Unable to make a get request", e);
                        out.write("Unable to make a get request");
                    }
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
