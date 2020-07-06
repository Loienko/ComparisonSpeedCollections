package net.ukr.dreamsicle.collections.typeOfCollection.arrayListAndLinkedList;

import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.collections.utils.ActionWithTypeOfWorkCollections;
import net.ukr.dreamsicle.collections.utils.CurrentTime;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UpdateElementToArrayAndLinkedList implements ActionWithTypeOfWorkCollections<List<Integer>> {
    CurrentTime currentTime = new CurrentTime() {
    };

    @Override
    public Long startOfTheList(List<Integer> list, long amount) {
        Long start = currentTime.time();
        for (int i = 0; i < amount; i++) {
            list.set(0, i);
        }
        return currentTime.time() - start;
    }

    @Override
    public Long middleOfTheList(List<Integer> list, long amount) {
        Long start = currentTime.time();
        for (int i = 0; i < amount; i++) {
            list.set(list.size() / 2, i);
        }
        return currentTime.time() - start;
    }

    @Override
    public Long endOfTheList(List<Integer> list, long amount) {
        Long start = currentTime.time();
        for (long i = 0; i < amount; i++) {
            list.set((int) i, (int) i);
        }
        return currentTime.time() - start;
    }
}
