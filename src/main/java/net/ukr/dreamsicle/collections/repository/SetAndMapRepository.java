package net.ukr.dreamsicle.collections.repository;

import net.ukr.dreamsicle.collections.model.entity.SetAndMapCollections;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetAndMapRepository extends JpaRepository<SetAndMapCollections, Long> {
}
