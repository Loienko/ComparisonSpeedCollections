package net.ukr.dreamsicle.collections.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.collections.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.collections.model.entity.TypeOfAction;
import net.ukr.dreamsicle.collections.repository.TypeOfActionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TypeOfActionService {
    private final TypeOfActionRepository typeOfActionRepository;

    @SneakyThrows
    public TypeOfAction getNameOfTypeActions(String typeOfAction) {
        log.info("Start getTypeOfActionName with param: {},", typeOfAction);
        return typeOfActionRepository.findByName(typeOfAction)
                .orElseThrow(() -> new ResourceNotFoundException("Resource type of action: " + typeOfAction + ", not found!"));
    }
}
