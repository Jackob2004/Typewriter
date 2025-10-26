package com.jackob.typewriter.commands;

import com.jackob.typewriter.manager.HologramManager;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestRemoveCommand implements BasicCommand {

    private final HologramManager hologramManager;

    public TestRemoveCommand(final HologramManager hologramManager) {
        this.hologramManager = hologramManager;
    }

    @Override
    public void execute(@NotNull CommandSourceStack source, String @NotNull [] args) {
        if (!(source.getSender() instanceof Player)) return;

        hologramManager.unregisterAllDisplays();
    }
}
