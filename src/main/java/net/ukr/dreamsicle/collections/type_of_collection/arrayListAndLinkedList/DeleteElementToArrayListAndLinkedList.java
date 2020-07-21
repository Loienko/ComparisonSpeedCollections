package net.ukr.dreamsicle.collections.type_of_collection.arrayListAndLinkedList;

import net.ukr.dreamsicle.collections.utils.ActionWithTypeOfWorkCollections;
import net.ukr.dreamsicle.collections.utils.CurrentTime;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeleteElementToArrayListAndLinkedList implements ActionWithTypeOfWorkCollections<List<Integer>> {

    CurrentTime currentTime = new CurrentTime() {
    };

    @Override
    public Long startOfTheList(List<Integer> list, long amount) {
        Long start = currentTime.time();
        for (long i = 0; i < list.size(); i++) {
            list.remove(0);
        }
        return currentTime.time() - start;
    }

    @Override
    public Long middleOfTheList(List<Integer> list, long amount) {
        Long start = currentTime.time();
        for (long i = 0; i < amount; i++) {
            list.remove(i / 2);
        }
        return currentTime.time() - start;
    }

    @Override
    public Long endOfTheList(List<Integer> list, long amount) {
        Long start = currentTime.time();
        for (long i = (amount - 1); i >= 0; i--) {
            list.remove(list.size() - 1);
        }
        return currentTime.time() - start;
    }
}
