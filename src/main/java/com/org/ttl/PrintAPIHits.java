package com.org.ttl;

import com.org.model.StatsInternal;

public class PrintAPIHits implements TTLConcurrentHashMapListener<String, StatsInternal> {

    @Override
    public void notifyOnAdd(String key, StatsInternal value) {}

    @Override
    public void notifyOnRemoval(String key, StatsInternal value) {
        System.out.println("Refreshing API Hits " + key + " : " + value);
    }
}
