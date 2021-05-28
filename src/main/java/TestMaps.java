import java.util.Map;

public abstract class TestMaps {
    // индекс для начала вставки элементов в Map
    private final int startIdx;
    // индекс для конца вставки элементов в Map
    private final int endIdx;
    // Шаг для увелечения вставляемого value
    private final int step;
    // Создание тестовой Map
    private final Map<Integer, Integer> testMap;

    protected TestMaps(int startIdx, int endIdx, int step, Map<Integer, Integer> testMap) {
        this.startIdx = startIdx;
        this.endIdx = endIdx;
        this.step = step;
        this.testMap = testMap;
    }
    /**
     * Пишет в Map от индекса -  startIdx, до индекса - endIdx
     * В value попадает += step
     */
    protected void writeMap() {
        for(int i = startIdx, j = startIdx; i < endIdx; i++, j += step) {
            testMap.put(i, j);
        }
    }
    /**
     * имитирует чтение из Map.
     */
    protected void readMap() {
        int emptyVal;
        for(int value : testMap.keySet()) {
//            emptyVal = testMap.get(value);
        }
    }
}
