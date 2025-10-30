package com.jackob.typewriter.manager;

import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class HologramManager {

    private final List<Display> displays;

    public HologramManager() {
        this.displays = new ArrayList<>();
    }

    public void registerDisplay(Display display) {
        this.displays.add(display);
    }

    public void unregisterAllDisplays() {
        this.displays.forEach(Entity::remove);
        displays.clear();
    }
}
