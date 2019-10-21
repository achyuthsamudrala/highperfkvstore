package requests;

public abstract class IncomingRequest {

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
