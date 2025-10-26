package com.jackob.typewriter.manager;

import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;

import java.util.HashSet;

public class HologramManager {

    private final HashSet<Display> displays;

    public HologramManager() {
        this.displays = new HashSet<>();
    }

    public void registerDisplay(Display display) {
        this.displays.add(display);
    }

    public void unregisterAllDisplays() {
        this.displays.forEach(Entity::remove);
        displays.clear();
    }
}
