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
@Table(name = "set_and_map_collections")
public class SetAndMapCollections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long amount;
    private String insert;
    private String retrieve;
    private String remove;

    @CreatedDate
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp created;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "name_of_collection")
    private NameOfCollections nameOfCollection;
}
