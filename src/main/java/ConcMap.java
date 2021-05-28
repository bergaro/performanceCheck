import java.util.concurrent.ConcurrentHashMap;

public class ConcMap extends TestMaps implements Runnable{


    protected ConcMap(int startIdx, int endIdx, int step) {
        super(startIdx, endIdx, step, new ConcurrentHashMap<>());
    }

    @Override
    public void run() {
        writeMap();
        readMap();
        Thread.currentThread().interrupt();
    }
}
