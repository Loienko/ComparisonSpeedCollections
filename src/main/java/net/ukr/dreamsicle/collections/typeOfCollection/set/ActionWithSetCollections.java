package net.ukr.dreamsicle.collections.typeOfCollection.set;

import net.ukr.dreamsicle.collections.model.entity.NameOfCollections;
import net.ukr.dreamsicle.collections.model.entity.SetAndMapCollections;
import net.ukr.dreamsicle.collections.utils.CurrentTime;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class ActionWithSetCollections implements CurrentTime {
    public static final String TIME_MS = " [ms]";

    public SetAndMapCollections createAndSaveExecuteDataIntoDb(long amount, NameOfCollections nameOfCollections) {
        return SetAndMapCollections.builder()
                .nameOfCollection(nameOfCollections)
                .amount(amount)
                .insert(insert(amount) + TIME_MS)
                .remove(remove(amount) + TIME_MS)
                .retrieve(retrieve(amount) + TIME_MS)
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

    public Long insert(long amount) {
        HashSet<Integer> hashSet = new HashSet<>();
        Long start = time();
        for (int i = 0; i < amount; i++) {
            hashSet.add(i);
        }
        return time() - start;
    }

    public Long remove(long amount) {
        HashSet<Integer> hashSet = getFillSet(new HashSet<>(), amount);
        Long start = time();
        for (int i = 0; i < amount; i++) {
            hashSet.remove(i);
        }
        return time() - start;
    }

    public Long retrieve(long amount) {
        HashSet<Integer> hashSet = getFillSet(new HashSet<>(), amount);
        Long start = time();

        return time() - start;
    }

    private synchronized HashSet<Integer> getFillSet(HashSet<Integer> hashSet, long amount) {
        for (int i = 0; i < amount; i++) {
            hashSet.add(i);
        }
        return hashSet;
    }
}
