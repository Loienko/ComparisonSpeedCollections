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
public class SetAndMapCollectionsDTO {
    private long id;
    private Long amount;
    private String insert;
    private String retrieve;
    private String remove;
    private Timestamp created;
    private String nameOfCollection;
}
