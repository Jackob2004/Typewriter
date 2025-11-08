package com.jackob.typewriter;

import com.jackob.typewriter.commands.TestCommand;
import com.jackob.typewriter.commands.TestRemoveCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Typewriter extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommand("writer", new TestCommand(this));
        registerCommand("remove", new TestRemoveCommand());
    }

    @Override
    public void onDisable() {
    }

}
