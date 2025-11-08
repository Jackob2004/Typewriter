package com.jackob.typewriter.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestRemoveCommand implements BasicCommand {

    public TestRemoveCommand() {
    }

    @Override
    public void execute(@NotNull CommandSourceStack source, String @NotNull [] args) {
        if (!(source.getSender() instanceof Player)) return;

    }
}
