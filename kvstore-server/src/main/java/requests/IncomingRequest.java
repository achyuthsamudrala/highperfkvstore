package requests;

import java.io.Serializable;

public class IncomingRequest implements Serializable {

    private static final long serialVersionUID = 3846195790307310909L;

    private final String requestType;
    private final byte[] payload;

    public IncomingRequest(String request, byte[] payload) {
        this.requestType = request;
        this.payload = payload;
    }

    public String getRequestType() {
        return requestType;
    }

    public byte[] getPayload() {
        return payload;
    }
}
