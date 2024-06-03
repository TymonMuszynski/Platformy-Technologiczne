package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MageRepository {
    private Map<String, Mage> collection;

    public MageRepository() {
        this.collection = new HashMap<>();
    }

    public Optional<Mage> find(String name) {
        return Optional.ofNullable(collection.get(name));
    }

    public String delete(String name) {
        if (!collection.containsKey(name)) {
            throw new IllegalArgumentException("Mage with name " + name + " not found");
        }
        collection.remove(name);
        return name;
    }

    public String save(Mage mage) {
        if (collection.containsKey(mage.getName())) {
            throw new IllegalArgumentException("Mage with name " + mage.getName() + " already exists");
        }
        collection.put(mage.getName(), mage);
        return "done";
    }
}
