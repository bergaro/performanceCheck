import java.util.*;
import java.util.concurrent.*;

/**
 * Выводы:
 * Collections.synchronizedMap() - хорош, когда согласованность данных имеет значение.
 * ConcurrentHashMap<K,V> - хорош, когда операций записи гораздо больше, чем операций чтения.
 *
 *  Разница довольно существенная. Ниже предоставлены метрики одного запуска теста.
 * __________________________________________Метрики________________________________________________
 *  Начало теста для: java.util.Collections$SynchronizedMap
 * 200000 записей вставлено/извлечено за 1539 мСек.
 * 200000 записей вставлено/извлечено за 1433 мСек.
 * 200000 записей вставлено/извлечено за 1405 мСек.
 * 200000 записей вставлено/извлечено за 1312 мСек.
 * 200000 записей вставлено/извлечено за 1299 мСек.
 * 200000 записей вставлено/извлечено за 1354 мСек.
 * 200000 записей вставлено/извлечено за 1346 мСек.
 * 200000 записей вставлено/извлечено за 1286 мСек.
 * Среднее время работы class java.util.Collections$SynchronizedMap составило 1371 мСек
 *
 * Начало теста для: java.util.concurrent.ConcurrentHashMap
 * 200000 записей вставлено/извлечено за 647 мСек.
 * 200000 записей вставлено/извлечено за 728 мСек.
 * 200000 записей вставлено/извлечено за 691 мСек.
 * 200000 записей вставлено/извлечено за 648 мСек.
 * 200000 записей вставлено/извлечено за 610 мСек.
 * 200000 записей вставлено/извлечено за 653 мСек.
 * 200000 записей вставлено/извлечено за 634 мСек.
 * 200000 записей вставлено/извлечено за 648 мСек.
 * Среднее время работы class java.util.concurrent.ConcurrentHashMap составило 657 мСек
 *
 */

public class Main {
    // Размерность пула потоков
    public final static int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    // Кол-во записей для вставки
    public final static int INPUT_LIMIT = 200_000;
    // Размерность Map
    public final static int MAP_SIZE = (int)Math.ceil(INPUT_LIMIT * 1.4);
    // synchronizedMap с заданной размерностью
    public static Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>(MAP_SIZE));
    // ConcurrentHashMap с заданной размерностью
    public static Map<String, Integer> concurrentHashMap = new ConcurrentHashMap<>(MAP_SIZE);

    public static void main(String[] args) throws InterruptedException {
        getTestMap(syncMap);
        getTestMap(concurrentHashMap);
    }
    /**
     * В зависимости от конфигурации процессора n-ое кол-во
     * раз запускает в пуле потоки. Замеряет время начала, конца работы пула потоков(размерностьПула - n-потоков)
     * и высчитывает среднее время n-ого числа запусков пула.
     * @param sthMap мапа для работы
     * @throws InterruptedException
     */
    public static void getTestMap(Map<String, Integer> sthMap) throws InterruptedException {
        System.out.println("Начало теста для: " + sthMap.getClass().getName());
        long averageTime = 0;
        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            long startTime = System.nanoTime();
            ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
            for (int j = 0; j < THREAD_POOL_SIZE; j++) {
                threadPool.execute(getScenario(sthMap));
            }
            threadPool.shutdown();
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            long entTime = System.nanoTime();
            long totalTime = (entTime - startTime) / 1_000_000L;
            averageTime += totalTime;
            System.out.println(INPUT_LIMIT + " записей вставлено/извлечено за " + totalTime + " мСек.");
        }
        System.out.println("Среднее время работы " + sthMap.getClass() +
                " составило " + averageTime / THREAD_POOL_SIZE + " мСек\n");
    }
    /**
     * Задаёт сценарий работы для потока
     * @param sthMap Мапа для работы
     * @return Runnable
     */
    public static Runnable getScenario(Map<String, Integer> sthMap) {
        return () -> {
            for (int i = 0; i < INPUT_LIMIT; i++) {
                Integer randomNumber = (int) Math.ceil(Math.random() * INPUT_LIMIT);
                // Вставка в мапу
                sthMap.put(String.valueOf(randomNumber), randomNumber);
                // имитация выборки
                Integer sthValue = sthMap.get(String.valueOf(randomNumber));
            }
        };
    }
}

