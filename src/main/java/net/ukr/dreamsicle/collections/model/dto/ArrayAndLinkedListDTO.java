package net.ukr.dreamsicle.collections.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArrayAndLinkedListDTO {
    private long id;
    private Long amount;
    private String startOfList;
    private String middleOfList;
    private String endOfList;
    private Timestamp created;
    private String nameOfCollection;
    private String typeOfAction;
}
