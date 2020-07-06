package net.ukr.dreamsicle.collections.typeOfCollection.map;

import net.ukr.dreamsicle.collections.model.entity.NameOfCollections;
import net.ukr.dreamsicle.collections.model.entity.SetAndMapCollections;
import net.ukr.dreamsicle.collections.utils.CurrentTime;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ActionWithMapCollections implements CurrentTime {
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
        Map<Integer, Integer> hashMap = getFillMap(new HashMap<>(), amount);
        Long start = time();
        for (int i = 0; i < amount; i++) {
            hashMap.put(i, i);
        }
        return time() - start;
    }

    public Long remove(long amount) {
        Map<Integer, Integer> hashMap = getFillMap(new HashMap<>(), amount);
        Long start = time();
        for (int i = 0; i < amount; i++) {
            hashMap.remove(i);
        }
        return time() - start;
    }

    public Long retrieve(long amount) {
        Map<Integer, Integer> hashMap = getFillMap(new HashMap<>(), amount);
        Long start = time();
        for (int i = 0; i < amount; i++) {
            hashMap.get(i);
        }
        return time() - start;
    }

    private synchronized Map<Integer, Integer> getFillMap(HashMap<Integer, Integer> hashMap, long amount) {
        for (int i = 0; i < amount; i++) {
            hashMap.put(i, i);
        }
        return hashMap;
    }
}
