package net.ukr.dreamsicle.collections.utils;

import net.ukr.dreamsicle.collections.model.entity.NameOfCollections;

public class NameOfCollectionsProvider {
    public static final long ID = 1L;

    public static NameOfCollections getNameOfCollectionsProvider(String nameOfCollection) {
        return NameOfCollections.builder()
                .id(ID)
                .name(nameOfCollection)
                .build();
    }
}
