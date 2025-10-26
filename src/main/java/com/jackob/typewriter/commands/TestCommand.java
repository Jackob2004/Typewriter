package com.jackob.typewriter.commands;

import com.jackob.typewriter.Typewriter;
import com.jackob.typewriter.objects.Writer;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements BasicCommand {

    private final Typewriter plugin;

    public TestCommand(Typewriter plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(@NotNull CommandSourceStack source, String @NotNull [] args) {
        if (!(source.getSender() instanceof Player p)) return;

        final Writer writer = new Writer.Builder()
                .type("Test\n")
                .build(plugin);

        writer.start(p);
    }

}
