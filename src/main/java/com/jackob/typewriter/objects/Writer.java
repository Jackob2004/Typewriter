package com.jackob.typewriter.objects;

import com.jackob.typewriter.Typewriter;
import com.jackob.typewriter.tasks.*;
import com.jackob.typewriter.utils.WriterUtil;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import java.util.LinkedList;
import java.util.Queue;

public class Writer {

    private final Typewriter plugin;

    private final Queue<WriterTask> tasks;

    private final int maxWordLength;

    private AnimationContext context;

    Writer(Typewriter plugin, Builder builder) {
        this.plugin = plugin;
        this.tasks = builder.tasks;
        this.maxWordLength = builder.maxWordLength;
    }

    public void start(Player player) {
        final String invisiblePart = "W".repeat(this.maxWordLength + 4);
        final TextDisplay background = WriterUtil.spawnDisplay(player, true, invisiblePart);
        final TextDisplay display = WriterUtil.spawnDisplay(player, false, "");

        context = new AnimationContext(display, maxWordLength);

        plugin.getManager().registerDisplay(background);
        plugin.getManager().registerDisplay(display);

        executeNext();
    }

    private void executeNext() {
        if (tasks.isEmpty()) {
            return;
        }

        tasks.poll().execute(plugin, this::executeNext, context);
    }

    public static class Builder {
        private final Queue<WriterTask> tasks = new LinkedList<>();

        private int maxWordLength = 0;

        public Builder type(String text) {
            maxWordLength = Math.max(maxWordLength, text.length());
            tasks.offer(new TypingTask(text));
            return this;
        }

        public Builder erase(int charsToErase) {
            tasks.offer(new ErasingTask(charsToErase));
            return this;
        }

        public Builder delete() {
            tasks.offer(new DeleteTask());
            return this;
        }

        public Builder pause(int repetitions) {
            tasks.offer(new PauseTask(repetitions));
            return this;
        }

        public Writer build(Typewriter plugin) {
            return new Writer(plugin,this);
        }
    }
}
