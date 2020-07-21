package net.ukr.dreamsicle.collections.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.collections.model.dto.ArrayAndLinkedListDTO;
import net.ukr.dreamsicle.collections.model.dto.ArrayAndLinkedListMapper;
import net.ukr.dreamsicle.collections.model.entity.ArrayAndLinkedList;
import net.ukr.dreamsicle.collections.model.entity.NameOfCollections;
import net.ukr.dreamsicle.collections.model.entity.TypeOfAction;
import net.ukr.dreamsicle.collections.repository.ArrayAndLinkedListRepository;
import net.ukr.dreamsicle.collections.type_of_collection.arrayListAndLinkedList.DeleteElementToArrayListAndLinkedList;
import net.ukr.dreamsicle.collections.type_of_collection.arrayListAndLinkedList.InsertElementToArrayAndLinkedList;
import net.ukr.dreamsicle.collections.type_of_collection.arrayListAndLinkedList.RetrieveElementToArrayAndLinkedList;
import net.ukr.dreamsicle.collections.type_of_collection.arrayListAndLinkedList.UpdateElementToArrayAndLinkedList;
import net.ukr.dreamsicle.collections.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Service
@RequiredArgsConstructor
@Slf4j
public class ArrayAndLinkedListService implements Utils {

    private final Executor threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private final TypeOfActionService typeOfActionService;
    private final NameOfCollectionService nameOfCollectionService;
    private final ArrayAndLinkedListRepository arrayAndLinkedListRepository;
    private final ArrayAndLinkedListMapper arrayAndLinkedListMapper;

    private final RetrieveElementToArrayAndLinkedList retrieveElementToArrayAndLinkedList;
    private final InsertElementToArrayAndLinkedList insertElementToArrayAndLinkedList;
    private final UpdateElementToArrayAndLinkedList updateElementToArrayAndLinkedList;
    private final DeleteElementToArrayListAndLinkedList deleteElementToArrayListAndLinkedList;

    volatile ArrayList<Integer> arrayList = new ArrayList<>();
    volatile LinkedList<Integer> linkedList = new LinkedList<>();

    /**
     * Retrieve all sort data by Id order by ASC
     *
     * @return List of the ArrayAndLinkedList collections
     */
    public Page<ArrayAndLinkedListDTO> retrieveAllData(Pageable pageable) {
        log.info("Start retrieveAllData for ArrayAndLinkedList for page: {}", pageable);
        Page<ArrayAndLinkedList> all = arrayAndLinkedListRepository.findAll(pageable);
        return arrayAndLinkedListMapper.arrayAndLinkedListToDTOs(arrayAndLinkedListRepository.findAll(pageable));
    }

    public List<ArrayAndLinkedListDTO> retrieve(long amount) {
        return arrayAndLinkedListMapper.arrayAndLinkedListToDTOs(getSpeedResultOfAllCollections(
                CompletableFuture.supplyAsync(
                        () -> createAndSaveData(
                                amount,
                                nameOfCollectionService.getNameOfCollections(ARRAY_LIST),
                                arrayList,
                                typeOfActionService.getNameOfTypeActions(RETRIEVE)),
                        threadPool),
                CompletableFuture.supplyAsync(
                        () -> createAndSaveData(
                                amount,
                                nameOfCollectionService.getNameOfCollections(LINKED_LIST),
                                linkedList,
                                typeOfActionService.getNameOfTypeActions(RETRIEVE)),
                        threadPool)
        ));
    }

    public List<ArrayAndLinkedListDTO> insert(long amount) {
        return arrayAndLinkedListMapper.arrayAndLinkedListToDTOs(getSpeedResultOfAllCollections(
                CompletableFuture.supplyAsync(
                        () -> createAndInsertData(
                                amount,
                                nameOfCollectionService.getNameOfCollections(ARRAY_LIST),
                                arrayList,
                                typeOfActionService.getNameOfTypeActions(INSERT)),
                        threadPool),
                CompletableFuture.supplyAsync(
                        () -> createAndInsertData(
                                amount,
                                nameOfCollectionService.getNameOfCollections(LINKED_LIST),
                                linkedList,
                                typeOfActionService.getNameOfTypeActions(INSERT)),
                        threadPool)
        ));
    }

    public List<ArrayAndLinkedListDTO> update(long amount) {
        return arrayAndLinkedListMapper.arrayAndLinkedListToDTOs(getSpeedResultOfAllCollections(
                CompletableFuture.supplyAsync(
                        () -> createAndUpdateData(
                                amount,
                                nameOfCollectionService.getNameOfCollections(ARRAY_LIST),
                                arrayList,
                                typeOfActionService.getNameOfTypeActions(UPDATE)),
                        threadPool),
                CompletableFuture.supplyAsync(
                        () -> createAndUpdateData(
                                amount,
                                nameOfCollectionService.getNameOfCollections(LINKED_LIST),
                                linkedList,
                                typeOfActionService.getNameOfTypeActions(UPDATE)),
                        threadPool)
        ));
    }

    public List<ArrayAndLinkedListDTO> delete(long amount) {
        return arrayAndLinkedListMapper.arrayAndLinkedListToDTOs(getSpeedResultOfAllCollections(
                CompletableFuture.supplyAsync(
                        () -> createAndDeleteData(
                                amount,
                                nameOfCollectionService.getNameOfCollections(ARRAY_LIST),
                                arrayList,
                                typeOfActionService.getNameOfTypeActions(DELETE)),
                        threadPool),
                CompletableFuture.supplyAsync(
                        () -> createAndDeleteData(
                                amount,
                                nameOfCollectionService.getNameOfCollections(LINKED_LIST),
                                linkedList,
                                typeOfActionService.getNameOfTypeActions(DELETE)),
                        threadPool)
        ));
    }

    @SneakyThrows({ExecutionException.class, InterruptedException.class})
    private List<ArrayAndLinkedList> getSpeedResultOfAllCollections(CompletableFuture<ArrayAndLinkedList> arrayList,
                                                                    CompletableFuture<ArrayAndLinkedList> linkedList) {
        return CompletableFuture.allOf(arrayList, linkedList)
                .thenApplyAsync(var -> Stream.of(arrayList, linkedList)
                        .map(value -> arrayAndLinkedListRepository.saveAndFlush(value.join()))
                        .collect(Collectors.toList()))
                .get();
    }

    private synchronized List<Integer> getListWithAmountOfElements(List<Integer> list, Long amount) {
        if (Objects.nonNull(amount)) {
            for (int i = 0; i < amount; i++) {
                list.add(i);
            }
        }
        return list;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArrayAndLinkedList createAndSaveData(long amount, NameOfCollections nameOfCollection, List<Integer> list, TypeOfAction typeOfAction) {
        return arrayAndLinkedListRepository.saveAndFlush(
                ArrayAndLinkedList.builder()
                        .amount(amount)
                        .startOfList(retrieveElementToArrayAndLinkedList.startOfTheList(getListWithAmountOfElements(list, amount), amount) + TIME_MS)
                        .middleOfList(retrieveElementToArrayAndLinkedList.middleOfTheList(getListWithAmountOfElements(list, amount), amount) + TIME_MS)
                        .endOfList(retrieveElementToArrayAndLinkedList.endOfTheList(getListWithAmountOfElements(list, amount), amount) + TIME_MS)
                        .nameOfCollection(nameOfCollection)
                        .created(Timestamp.valueOf(LocalDateTime.now()))
                        .typeOfAction(typeOfAction)
                        .build());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArrayAndLinkedList createAndInsertData(long amount, NameOfCollections nameOfCollection, List<Integer> list, TypeOfAction typeOfAction) {
        return arrayAndLinkedListRepository.saveAndFlush(
                ArrayAndLinkedList.builder()
                        .amount(amount)
                        .startOfList(insertElementToArrayAndLinkedList.startOfTheList(getListWithAmountOfElements(list, amount), amount) + TIME_MS)
                        .middleOfList(insertElementToArrayAndLinkedList.middleOfTheList(getListWithAmountOfElements(list, amount), amount) + TIME_MS)
                        .endOfList(insertElementToArrayAndLinkedList.endOfTheList(getListWithAmountOfElements(list, amount), amount) + TIME_MS)
                        .nameOfCollection(nameOfCollection)
                        .created(Timestamp.valueOf(LocalDateTime.now()))
                        .typeOfAction(typeOfAction)
                        .build());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArrayAndLinkedList createAndUpdateData(long amount, NameOfCollections nameOfCollection, List<Integer> list, TypeOfAction typeOfAction) {
        return arrayAndLinkedListRepository.saveAndFlush(
                ArrayAndLinkedList.builder()
                        .amount(amount)
                        .startOfList(updateElementToArrayAndLinkedList.startOfTheList(getListWithAmountOfElements(list, amount), amount) + TIME_MS)
                        .middleOfList(updateElementToArrayAndLinkedList.middleOfTheList(getListWithAmountOfElements(list, amount), amount) + TIME_MS)
                        .endOfList(updateElementToArrayAndLinkedList.endOfTheList(getListWithAmountOfElements(list, amount), amount) + TIME_MS)
                        .nameOfCollection(nameOfCollection)
                        .created(Timestamp.valueOf(LocalDateTime.now()))
                        .typeOfAction(typeOfAction)
                        .build());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ArrayAndLinkedList createAndDeleteData(long amount, NameOfCollections nameOfCollection, List<Integer> list, TypeOfAction typeOfAction) {
        return arrayAndLinkedListRepository.saveAndFlush(
                ArrayAndLinkedList.builder()
                        .amount(amount)
                        .startOfList(deleteElementToArrayListAndLinkedList.startOfTheList(getListWithAmountOfElements(list, amount), amount) + TIME_MS)
                        .middleOfList(deleteElementToArrayListAndLinkedList.middleOfTheList(getListWithAmountOfElements(list, amount), amount) + TIME_MS)
                        .endOfList(deleteElementToArrayListAndLinkedList.endOfTheList(getListWithAmountOfElements(list, amount), amount) + TIME_MS)
                        .nameOfCollection(nameOfCollection)
                        .created(Timestamp.valueOf(LocalDateTime.now()))
                        .typeOfAction(typeOfAction)
                        .build());
    }

    @PostConstruct
    public void postConstructMethod() {
        log.warn("Spring Bean Post Construct Annotation Method ");
    }

    @PreDestroy
    public void preDestroy() {
        log.warn("Spring Bean Pre Destroy Annotation Method");
    }
}
