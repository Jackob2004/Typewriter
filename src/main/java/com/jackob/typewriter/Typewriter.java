package com.jackob.typewriter;

import com.jackob.typewriter.commands.TestCommand;
import com.jackob.typewriter.commands.TestRemoveCommand;
import com.jackob.typewriter.manager.HologramManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Typewriter extends JavaPlugin {

    private HologramManager manager;

    @Override
    public void onEnable() {
        this.manager = new HologramManager();

        registerCommand("writer", new TestCommand(this));
        registerCommand("remove", new TestRemoveCommand(manager));
    }

    @Override
    public void onDisable() {
        if (manager != null) {
            manager.unregisterAllDisplays();
        }
    }

    public HologramManager getManager() { return manager; }
}
