package com.jimuv.common.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FFmpegIdUtils {

    private final ConcurrentHashMap<String, AtomicInteger> sequenceMap;

    public FFmpegIdUtils() {
        sequenceMap = new ConcurrentHashMap<>();
    }

    public int getNextId(String id) {
        return sequenceMap.computeIfAbsent(id, k -> new AtomicInteger(0)).incrementAndGet();
    }

    public int getNextIdFromZero(String id) {
        return sequenceMap.computeIfAbsent(id, k -> new AtomicInteger(0)).incrementAndGet() - 1;
    }

    public String inputV() {
        return "[" + getNextId("inputV") + ":v]";
    }

    public String outputV() {
        return "[outputV" + getNextId("outputV") + "]";
    }

    public String inputA() {
        return "[" + getNextIdFromZero("inputA") + ":a]";
    }

    public String outputA() {
        return "[outputA" + getNextId("outputA") + "]";
    }

    public void removeSequence(String id) {
        sequenceMap.remove(id);
    }

    public void clearAllSequences() {
        sequenceMap.clear();
    }
}