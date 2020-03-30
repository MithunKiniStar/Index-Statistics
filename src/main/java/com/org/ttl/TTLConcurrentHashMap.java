package com.org.ttl;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author  Mithun Kini
 *
 * This map is configured for Time To Live (TTL)
 * Based on the the expiryInMillis, the records get cleaned up after they meet the expiry period.
 * This will ensure that no stale records will be stored in memory collection so that the performance is boosted.
 *
 * @param <K>
 * @param <V>
 */
public class TTLConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V> {

    private static final long serialVersionUID = 1L;

    private Map<K, Long> timeMap = new ConcurrentHashMap<K, Long>();
    private TTLConcurrentHashMapListener<K, V> listener;
    private long expiryInMillis;
    private boolean mapAlive = true;

    public TTLConcurrentHashMap() {
        this.expiryInMillis = 10000;
        initialize();
    }

    public TTLConcurrentHashMap(TTLConcurrentHashMapListener<K, V> listener) {
        this.listener = listener;
        this.expiryInMillis = 10000;
        initialize();
    }

    public TTLConcurrentHashMap(long expiryInMillis) {
        this.expiryInMillis = expiryInMillis;
        initialize();
    }

    public TTLConcurrentHashMap(long expiryInMillis, TTLConcurrentHashMapListener<K, V> listener) {
        this.expiryInMillis = expiryInMillis;
        this.listener = listener;
        initialize();
    }

    void initialize() {
        new CleanerThread().start();
    }

    public void registerRemovalListener(TTLConcurrentHashMapListener<K, V> listener) {
        this.listener = listener;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalStateException if trying to insert values into map after quiting
     */
    @Override
    public V put(K key, V value) {
        if (!mapAlive) {
            throw new IllegalStateException("WeakConcurrent Hashmap is no more alive.. Try creating a new one.");	// No I18N
        }
        Date date = new Date();
        timeMap.put(key, date.getTime());
        V returnVal = super.put(key, value);
        if (listener != null) {
            listener.notifyOnAdd(key, value);
        }
        return returnVal;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalStateException if trying to insert values into map after quiting
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (!mapAlive) {
            throw new IllegalStateException("WeakConcurrent Hashmap is no more alive.. Try creating a new one.");	// No I18N
        }
        for (K key : m.keySet()) {
            put(key, m.get(key));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalStateException if trying to insert values into map after quiting
     */
    @Override
    public V putIfAbsent(K key, V value) {
        if (!mapAlive) {
            throw new IllegalStateException("WeakConcurrent Hashmap is no more alive.. Try creating a new one.");	// No I18N
        }
        if (!containsKey(key)) {
            return put(key, value);
        } else {
            return get(key);
        }
    }

    /**
     * Should call this method when it's no longer required
     */
    public void quitMap() {
        mapAlive = false;
    }

    public boolean isAlive() {
        return mapAlive;
    }



    public void recordEntry(K key){
        Date date = new Date();
        timeMap.put(key, date.getTime());
    }




    /**
     *
     * This thread performs the cleaning operation on the concurrent hashmap once in a specified interval.
     *
     *
     */
    class CleanerThread extends Thread {

        @Override
        public void run() {
            while (mapAlive) {
                cleanMap();
                try {
                    Thread.sleep(expiryInMillis );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void cleanMap() {
            long currentTime = new Date().getTime();
            System.out.println("Time isssssssssssssssssss "+currentTime);
            for (K key : timeMap.keySet()) {
                if (currentTime > (timeMap.get(key) + expiryInMillis)) {
                    V value = remove(key);
                    timeMap.remove(key);
                    if (listener != null) {
                        listener.notifyOnRemoval(key, value);
                    }
                }
            }
        }
    }
}