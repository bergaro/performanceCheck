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
    private static final int STEP = 79;
    // Промежутки для потока (delta[0] - начальный индекс вставки, delta[1] - крайний индекс вставки)
    private static final int[] delta1 = new int[]{0, 100_000};
    private static final int[] delta2 = new int[]{100_000, 500_000};
    private static final int[] delta3 = new int[]{200_000, 1_000_000};
    private static final int[] delta4 = new int[]{300_000, 150_000};
    private static final int[] delta5 = new int[]{400_00, 2_000_000};
    // Формат вывод времени теста
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");

    public static void main(String[] args) {
        concurrentHashMapTest();
        try{
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        simpleHashMapTest();
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
        startTime = dateFormat.format(new Date());
        System.out.println(title + "\nВремя начала: " + startTime);
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
        endTime = dateFormat.format(new Date());
        System.out.println("Время конца: " + endTime + "\n");
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
        startTime = dateFormat.format(new Date());
        System.out.println(title + "\nВремя начала: " + startTime);
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
        endTime = dateFormat.format(new Date());
        System.out.println("Время конца: " + endTime);
    }

}
