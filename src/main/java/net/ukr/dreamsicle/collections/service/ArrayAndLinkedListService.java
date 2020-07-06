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
import net.ukr.dreamsicle.collections.typeOfCollection.arrayListAndLinkedList.DeleteElementToArrayListAndLinkedList;
import net.ukr.dreamsicle.collections.typeOfCollection.arrayListAndLinkedList.InsertElementToArrayAndLinkedList;
import net.ukr.dreamsicle.collections.typeOfCollection.arrayListAndLinkedList.RetrieveElementToArrayAndLinkedList;
import net.ukr.dreamsicle.collections.typeOfCollection.arrayListAndLinkedList.UpdateElementToArrayAndLinkedList;
import net.ukr.dreamsicle.collections.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        log.info("Start retrieve for items of: {}", amount);
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
        log.info("Start insert for items of: {}", amount);
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
        log.info("Start insert for update of: {}", amount);
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
        log.info("Start delete for items of: {}", amount);
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
        log.info("Start Save Data: {}, {}, {}", amount, nameOfCollection, typeOfAction);
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
        log.info("Start Insert Data: {}, {}, {}", amount, nameOfCollection, typeOfAction);
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
        log.info("Start Update Data: {}, {}, {}", amount, nameOfCollection, typeOfAction);
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
        log.info("Start Delete Data: {}, {}, {}", amount, nameOfCollection, typeOfAction);
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
}
