package net.ukr.dreamsicle.collections.type_of_collection.arrayListAndLinkedList;

import net.ukr.dreamsicle.collections.utils.ActionWithTypeOfWorkCollections;
import net.ukr.dreamsicle.collections.utils.CurrentTime;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsertElementToArrayAndLinkedList implements ActionWithTypeOfWorkCollections<List<Integer>> {
    CurrentTime currentTime = new CurrentTime() {
    };

    @Override
    public Long startOfTheList(List<Integer> list, long amount) {
        Long start = currentTime.time();
        for (int i = 0; i < amount; i++) {
            list.add(0, i);
        }
        return currentTime.time() - start;
    }

    @Override
    public Long middleOfTheList(List<Integer> list, long amount) {
        Long start = currentTime.time();
        for (int i = 0; i < amount; i++) {
            list.add(list.size() / 2, i);
        }
        return currentTime.time() - start;
    }

    @Override
    public Long endOfTheList(List<Integer> list, long amount) {
        Long start = currentTime.time();
        for (int i = 0; i < amount; i++) {
            list.add(i);
        }
        return currentTime.time() - start;
    }
}
