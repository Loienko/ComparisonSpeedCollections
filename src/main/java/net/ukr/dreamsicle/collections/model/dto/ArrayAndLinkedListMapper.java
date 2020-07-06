package net.ukr.dreamsicle.collections.model.dto;

import net.ukr.dreamsicle.collections.model.entity.ArrayAndLinkedList;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArrayAndLinkedListMapper {
    public ArrayAndLinkedListDTO arrayAndLinkedListToDTO(ArrayAndLinkedList arrayAndLinkedList) {
        return ArrayAndLinkedListDTO.builder()
                .id(arrayAndLinkedList.getId())
                .amount(arrayAndLinkedList.getAmount())
                .nameOfCollection(arrayAndLinkedList.getNameOfCollection().getName())
                .typeOfAction(arrayAndLinkedList.getTypeOfAction().getName())
                .startOfList(arrayAndLinkedList.getStartOfList())
                .middleOfList(arrayAndLinkedList.getMiddleOfList())
                .endOfList(arrayAndLinkedList.getEndOfList())
                .created(arrayAndLinkedList.getCreated())
                .build();
    }

    public Page<ArrayAndLinkedListDTO> arrayAndLinkedListToDTOs(Page<ArrayAndLinkedList> arrayAndLinkedLists) {
        return arrayAndLinkedLists.map(this::arrayAndLinkedListToDTO);
    }

    public List<ArrayAndLinkedListDTO> arrayAndLinkedListToDTOs(List<ArrayAndLinkedList> arrayAndLinkedLists) {
        return arrayAndLinkedLists.stream().map(this::arrayAndLinkedListToDTO).collect(Collectors.toList());
    }
}
