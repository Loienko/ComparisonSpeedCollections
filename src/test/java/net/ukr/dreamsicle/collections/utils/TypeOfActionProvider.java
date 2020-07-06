package net.ukr.dreamsicle.collections.utils;

import net.ukr.dreamsicle.collections.model.entity.TypeOfAction;

public class TypeOfActionProvider {
    public static final long ID = 1L;

    public static TypeOfAction getTypeOfActionProvider(String typeOfAction) {
        return TypeOfAction.builder()
                .id(ID)
                .name(typeOfAction)
                .build();
    }
}
