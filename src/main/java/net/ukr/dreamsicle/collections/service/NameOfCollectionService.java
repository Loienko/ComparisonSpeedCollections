package net.ukr.dreamsicle.collections.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.collections.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.collections.model.entity.NameOfCollections;
import net.ukr.dreamsicle.collections.repository.NameOfCollectionsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NameOfCollectionService {

    private final NameOfCollectionsRepository nameOfCollectionsRepository;

    @SneakyThrows
    public NameOfCollections getNameOfCollections(String nameOfCollections) {
        log.info("Start getNameOfCollections with param: {},", nameOfCollections);
        return nameOfCollectionsRepository.findByName(nameOfCollections)
                .orElseThrow(() -> new ResourceNotFoundException("Resource type of action: " + nameOfCollections + ", not found!"));
    }
}
