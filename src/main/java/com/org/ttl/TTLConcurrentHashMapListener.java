package com.org.ttl;

public interface TTLConcurrentHashMapListener<K,V> {
    public void notifyOnAdd(K key, V value);
    public void notifyOnRemoval(K key, V value);
}