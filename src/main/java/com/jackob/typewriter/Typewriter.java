package com.jackob.typewriter;

import com.jackob.typewriter.commands.TestCommand;
import com.jackob.typewriter.manager.CleanupManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Typewriter extends JavaPlugin {

    private CleanupManager cleanupManager;

    @Override
    public void onEnable() {
        cleanupManager = new CleanupManager();

        registerCommand("writer", new TestCommand(this));
    }

    @Override
    public void onDisable() {
        if (cleanupManager != null) {
            cleanupManager.removeAllEntities();
        }
    }

    public CleanupManager getCleanupManager() { return cleanupManager; }
}
