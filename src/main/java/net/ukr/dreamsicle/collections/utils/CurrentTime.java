package net.ukr.dreamsicle.collections.utils;

public interface CurrentTime {
    default Long time() {
        return System.currentTimeMillis();
    }
}
