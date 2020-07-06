package net.ukr.dreamsicle.collections.model.dto;

import net.ukr.dreamsicle.collections.model.entity.SetAndMapCollections;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetAndMapCollectionsMapper {

    public SetAndMapCollectionsDTO setAndMapCollectionsDTO(SetAndMapCollections setAndMapCollections) {
        return SetAndMapCollectionsDTO.builder()
                .id(setAndMapCollections.getId())
                .amount(setAndMapCollections.getAmount())
                .nameOfCollection(setAndMapCollections.getNameOfCollection().getName())
                .created(setAndMapCollections.getCreated())
                .insert(setAndMapCollections.getInsert())
                .remove(setAndMapCollections.getRemove())
                .retrieve(setAndMapCollections.getRetrieve())
                .build();
    }

    public Page<SetAndMapCollectionsDTO> setAndMapCollectionsDTOs(Page<SetAndMapCollections> setAndMapCollections) {
        return setAndMapCollections.map(this::setAndMapCollectionsDTO);
    }

    public List<SetAndMapCollectionsDTO> arrayAndLinkedListToDTOs(List<SetAndMapCollections> setAndMapCollections) {
        return setAndMapCollections.stream().map(this::setAndMapCollectionsDTO).collect(Collectors.toList());
    }
}
