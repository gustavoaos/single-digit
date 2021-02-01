package com.gustavoaos.singledigit.infrastructure.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private static final boolean ACCESS_ORDER = true;

    private final int maxEntries;

    public LRUCache(int maxEntries) {
        super(INITIAL_CAPACITY, LOAD_FACTOR, ACCESS_ORDER);
        this.maxEntries = maxEntries;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > maxEntries;
    }

}
