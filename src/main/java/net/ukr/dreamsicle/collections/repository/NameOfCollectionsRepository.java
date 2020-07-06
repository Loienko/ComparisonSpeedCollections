package net.ukr.dreamsicle.collections.repository;

import net.ukr.dreamsicle.collections.model.entity.NameOfCollections;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NameOfCollectionsRepository extends JpaRepository<NameOfCollections, Long> {

    Optional<NameOfCollections> findByName(String name);
}
