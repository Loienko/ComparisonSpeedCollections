package net.ukr.dreamsicle.collections.type_of_collection.arrayListAndLinkedList;

import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.collections.utils.ActionWithTypeOfWorkCollections;
import net.ukr.dreamsicle.collections.utils.CurrentTime;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RetrieveElementToArrayAndLinkedList implements ActionWithTypeOfWorkCollections<List<Integer>> {
    CurrentTime currentTime = new CurrentTime() {
    };

    @Override
    public Long startOfTheList(List<Integer> list, long amount) {
        Long start = currentTime.time();
        for (long i = 0; i < amount; i++) {
            list.get((int) i);
        }
        return currentTime.time() - start;
    }

    @Override
    public Long middleOfTheList(List<Integer> list, long amount) {
        Long start = currentTime.time();
        for (long i = 0; i < amount; i++) {
            list.get(list.size() / 2);
        }
        return currentTime.time() - start;
    }

    @Override
    public Long endOfTheList(List<Integer> list, long amount) {
        Long start = currentTime.time();
        for (long i = amount - 1; i >= 0; i--) {
            list.get((int) i);
        }
        return currentTime.time() - start;
    }
}
