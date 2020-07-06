package net.ukr.dreamsicle.collections.service;

import net.ukr.dreamsicle.collections.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.collections.model.entity.NameOfCollections;
import net.ukr.dreamsicle.collections.repository.NameOfCollectionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static net.ukr.dreamsicle.collections.utils.NameOfCollectionsProvider.getNameOfCollectionsProvider;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class NameOfCollectionServiceTest {

    @InjectMocks
    private NameOfCollectionService nameOfCollectionService;

    @Mock
    private NameOfCollectionsRepository nameOfCollectionsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetNameOfCollections() {
        final String collectionsName = "Set";
        NameOfCollections nameOfCollections = getNameOfCollectionsProvider(collectionsName);
        when(nameOfCollectionsRepository.findByName(collectionsName)).thenReturn(Optional.of(nameOfCollections));

        NameOfCollections nameOfCollectionsActual = nameOfCollectionService.getNameOfCollections(collectionsName);

        assertNotNull(nameOfCollections);
        assertEquals(collectionsName, nameOfCollectionsActual.getName());
    }

    @Test
    void testGetNameOfCollections_nameOfCollectionIsEmpty_returnException() {
        final String collectionsName = "Set";
        when(nameOfCollectionsRepository.findByName(collectionsName)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> nameOfCollectionsRepository.findByName(collectionsName));
    }
}