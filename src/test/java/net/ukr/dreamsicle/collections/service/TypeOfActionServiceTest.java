package net.ukr.dreamsicle.collections.service;

import net.ukr.dreamsicle.collections.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.collections.model.entity.TypeOfAction;
import net.ukr.dreamsicle.collections.repository.TypeOfActionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static net.ukr.dreamsicle.collections.utils.TypeOfActionProvider.getTypeOfActionProvider;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TypeOfActionServiceTest {

    @InjectMocks
    private TypeOfActionService typeOfActionService;

    @Mock
    private TypeOfActionRepository typeOfActionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getTypeOfActionName_correctData_successful() {
        final String typeOfActionName = "Insert";
        TypeOfAction typeOfAction = getTypeOfActionProvider(typeOfActionName);
        when(typeOfActionRepository.findByName(typeOfActionName)).thenReturn(Optional.of(typeOfAction));

        TypeOfAction typeOfActionNameActual = typeOfActionService.getNameOfTypeActions(typeOfActionName);

        assertNotNull(typeOfActionNameActual);
        assertEquals(typeOfActionName, typeOfActionNameActual.getName());
    }

    @Test
    void getTypeOfActionName_typeOfActionIsEmpty_returnException() {
        final String typeOfActionName = "Insert";
        when(typeOfActionRepository.findByName(typeOfActionName)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> typeOfActionService.getNameOfTypeActions(typeOfActionName));
    }
}