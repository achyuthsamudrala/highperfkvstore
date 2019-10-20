import memory.SSCollection;

public interface Driver {

    byte[] get(byte[] key);
    void set(byte[] key, byte[] value);
    SSCollection getSnapshot();

}
