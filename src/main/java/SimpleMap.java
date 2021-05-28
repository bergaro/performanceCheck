import java.util.Collections;
import java.util.HashMap;

public class SimpleMap extends TestMaps implements Runnable {

    protected SimpleMap(int startIdx, int endIdx, int step) {
        super(startIdx, endIdx, step, Collections.synchronizedMap(new HashMap<>()));
    }
    @Override
    public void run() {
        writeMap();
        readMap();
        Thread.currentThread().interrupt();
    }
}
