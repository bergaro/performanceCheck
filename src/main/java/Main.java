import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Выводы:
 * Collections.synchronizedMap() - хорош, когда согласованность данных имеет значение.
 * ConcurrentHashMap<K,V> - хорош, когда операций записи гораздо больше, чем операций чтения.
 *
 *  +/- разница, на мой взгляд, не большая. При мальенькой нагрузке, просадка в производительности synchronizedMap
 *  более заметна в рамках данного теста. Так же при полной нагрузке, много тестов, когда synchronizedMap был
 *  незначительно быстрее.
 * __________________________________________Метрики________________________________________________
 *  В среднем SynchronizedMap - выполняется за - 1891.0 ms
 *  А ConcurrentHashMap - выполняется за - 1023.0 ms
 *
 */

public class Main {
    // Заголовок теста
    private static String title;
    // Начальное время теста
    private static String startTime;
    // Конечное время теста
    private static String endTime;
    // Шаг для увелечения числа для вставки
    private static final int STEP = 1;
    // Промежутки для потока (delta[0] - начальный индекс вставки, delta[1] - крайний индекс вставки)
    private static final int[] delta1 = new int[]{0, 5_000_000};
    private static final int[] delta2 = new int[]{0, 5_000_000};
    private static final int[] delta3 = new int[]{0, 5_000_000};
    private static final int[] delta4 = new int[]{0, 5_000_000};
    private static final int[] delta5 = new int[]{0, 5_000_000};
    // Формат вывод времени теста
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("SSS");

    public static void main(String[] args) {
        simpleHashMapTest();
        try{
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        concurrentHashMapTest();
    }
    /**
     * Тест для concurrentHashMap.
     * 1) Создаётся n - потоков с разными промежутками для вставки значений в Map
     * 2) Ожидает исполненение потоков
     * 3) Замеряет время от старта потоков, до исполнения крайнего потока.
     * 4) Выводит результат в консоль
     */
    private static void concurrentHashMapTest() {
        title = "Test with concurrentHashMap";
//        startTime = dateFormat.format(new Date());
//        System.out.println(title + "\nВремя начала: " + startTime);
        long m = System.currentTimeMillis();
        Thread ct1 = new Thread(null, new ConcMap(delta1[0], delta1[1], STEP));
        Thread ct2 = new Thread(null, new ConcMap(delta2[0], delta2[1], STEP));
        Thread ct3 = new Thread(null, new ConcMap(delta3[0], delta3[1], STEP));
        Thread ct4 = new Thread(null, new ConcMap(delta4[0], delta4[1], STEP));
        Thread ct5 = new Thread(null, new ConcMap(delta5[0], delta5[1], STEP));
        ct1.start();
        ct2.start();
        ct3.start();
        ct4.start();
        ct5.start();
        try {
            ct5.join();
            ct4.join();
            ct3.join();
            ct2.join();
            ct1.join();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
//        endTime = dateFormat.format(new Date());
        System.out.println("Время конца: " + (double) (System.currentTimeMillis() - m) + "\n");
    }
    /**
     * Тест для synchronizedMap.
     * 1) Создаётся n - потоков с разными промежутками для вставки значений в Map
     * 2) Ожидает исполненение потоков
     * 3) Замеряет время от старта потоков, до исполнения крайнего потока.
     * 4) Выводит результат в консоль
     */
    private static void simpleHashMapTest() {
        title = "Test with synchronizedMap";
//        startTime = dateFormat.format(new Date());
//        System.out.println(title + "\nВремя начала: " + startTime);
        long m = System.currentTimeMillis();
        Thread ct1 = new Thread(null, new SimpleMap(delta1[0], delta1[1], STEP));
        Thread ct2 = new Thread(null, new SimpleMap(delta2[0], delta2[1], STEP));
        Thread ct3 = new Thread(null, new SimpleMap(delta3[0], delta3[1], STEP));
        Thread ct4 = new Thread(null, new SimpleMap(delta4[0], delta4[1], STEP));
        Thread ct5 = new Thread(null, new SimpleMap(delta5[0], delta5[1], STEP));
        ct1.start();
        ct2.start();
        ct3.start();
        ct4.start();
        ct5.start();
        try {
            ct5.join();
            ct4.join();
            ct3.join();
            ct2.join();
            ct1.join();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
//        endTime = dateFormat.format(new Date());
        System.out.println("Время конца: " + (double) (System.currentTimeMillis() - m) + "\n");
    }

}
