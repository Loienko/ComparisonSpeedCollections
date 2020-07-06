package net.ukr.dreamsicle.collections.repository;

import net.ukr.dreamsicle.collections.model.entity.TypeOfAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeOfActionRepository extends JpaRepository<TypeOfAction, Long> {
    Optional<TypeOfAction> findByName(String name);
}
