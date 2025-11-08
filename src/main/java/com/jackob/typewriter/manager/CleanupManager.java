package com.jackob.typewriter.manager;

import org.bukkit.entity.Entity;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CleanupManager {

    final Set<Entity> writerEntities;

    public CleanupManager() {
        writerEntities = new HashSet<>();
    }

    public void registerEntities(Entity... entities) {
        writerEntities.addAll(List.of(entities));
    }

    public void unregisterEntities(Entity... entities) {
        List.of(entities).forEach(writerEntities::remove);
    }

    public void removeAllEntities() {
        writerEntities.stream()
                .filter(Objects::nonNull)
                .filter(Entity::isValid)
                .forEach(Entity::remove);

        writerEntities.clear();
    }
}
