package net.ukr.dreamsicle.collections.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.collections.model.dto.SetAndMapCollectionsDTO;
import net.ukr.dreamsicle.collections.model.dto.SetAndMapCollectionsMapper;
import net.ukr.dreamsicle.collections.model.entity.SetAndMapCollections;
import net.ukr.dreamsicle.collections.repository.SetAndMapRepository;
import net.ukr.dreamsicle.collections.typeOfCollection.map.ActionWithMapCollections;
import net.ukr.dreamsicle.collections.typeOfCollection.set.ActionWithSetCollections;
import net.ukr.dreamsicle.collections.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class SetAndMapService implements Utils {
    private final Executor threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private final NameOfCollectionService nameOfCollectionService;
    private final SetAndMapRepository setAndMapRepository;
    private final SetAndMapCollectionsMapper setAndMapCollectionsMapper;

    private final ActionWithMapCollections actionWithMapCollections;
    private final ActionWithSetCollections actionWithSetCollections;

    public Page<SetAndMapCollectionsDTO> retrieveAllData(Pageable pageable) {
        log.info("Start retrieveAllData for SetAndMap for page: {}", pageable);
        return setAndMapCollectionsMapper.setAndMapCollectionsDTOs(setAndMapRepository.findAll(pageable));
    }

    public List<SetAndMapCollectionsDTO> allActionWithCollections(long amount) {
        log.info("Start allActionWithCollections for number of element: {}", amount);
        return setAndMapCollectionsMapper.arrayAndLinkedListToDTOs(getResultActionWithSetAndMapCollections(
                CompletableFuture.supplyAsync(
                        () -> actionWithMapCollections.createAndSaveExecuteDataIntoDb(amount, nameOfCollectionService.getNameOfCollections(MAP)),
                        threadPool),
                CompletableFuture.supplyAsync(
                        () -> actionWithSetCollections.createAndSaveExecuteDataIntoDb(amount, nameOfCollectionService.getNameOfCollections(SET)),
                        threadPool)
        ));
    }

    @SneakyThrows({ExecutionException.class, InterruptedException.class})
    private List<SetAndMapCollections> getResultActionWithSetAndMapCollections(CompletableFuture<SetAndMapCollections> map,
                                                                               CompletableFuture<SetAndMapCollections> set
    ) {
        return CompletableFuture.allOf(map, set)
                .thenApplyAsync(var -> Stream.of(map, set)
                        .map(value -> setAndMapRepository.saveAndFlush(value.join()))
                        .collect(Collectors.toList()))
                .get();
    }
}
