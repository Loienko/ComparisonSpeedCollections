package net.ukr.dreamsicle.collections.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "array_and_linked_list_collections")
public class ArrayAndLinkedList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long amount;
    private String startOfList;
    private String middleOfList;
    private String endOfList;

    @CreatedDate
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp created;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "name_of_collection")
    private NameOfCollections nameOfCollection;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_of_action")
    private TypeOfAction typeOfAction;

    
}
