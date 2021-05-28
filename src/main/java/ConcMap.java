import java.util.concurrent.ConcurrentHashMap;

public class ConcMap extends TestMaps implements Runnable{


    protected ConcMap(int startIdx, int endIdx, int step) {
        super(startIdx, endIdx, step, new ConcurrentHashMap<>());
    }

    @Override
    public void run() {
        long m = System.currentTimeMillis();
        writeMap();
        readMap();
        System.out.println("concВремя конца: " + (double) (System.currentTimeMillis() - m) + "\n");
    }
}
