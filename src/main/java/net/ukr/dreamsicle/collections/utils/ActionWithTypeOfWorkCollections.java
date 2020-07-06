package net.ukr.dreamsicle.collections.utils;

public interface ActionWithTypeOfWorkCollections<T> {
    Long startOfTheList(T list, long amount);

    Long middleOfTheList(T list, long amount);

    Long endOfTheList(T list, long amount);
}
