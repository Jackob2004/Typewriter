package com.jackob.typewriter.objects;

import com.jackob.typewriter.Typewriter;
import com.jackob.typewriter.tasks.*;
import com.jackob.typewriter.utils.WriterUtil;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import java.util.*;

public class Writer {

    private final Typewriter plugin;

    private final List<WriterTask> tasksSequence;

    private final Queue<WriterTask> tasksQueue;

    private final int maxWordLength;

    private final int lines;

    private final TextColor textColor;

    private final boolean loop;

    private AnimationContext context;

    Writer(Typewriter plugin, Builder builder) {
        this.plugin = plugin;
        this.tasksSequence = new ArrayList<>(builder.tasksSequence);
        this.tasksQueue = new LinkedList<>(tasksSequence);
        this.maxWordLength = builder.maxWordLength;
        this.lines = Math.toIntExact(builder.wholeText.toString().lines().count());
        this.textColor = builder.textColor;
        this.loop = builder.loop;
    }

    public void start(Player player) {
        final String invisibleText = "W".repeat(this.maxWordLength + 4) + "\n".repeat(lines - 1);
        final TextDisplay background = WriterUtil.spawnDisplay(player, true, invisibleText);
        final TextDisplay display = WriterUtil.spawnDisplay(player, false, "");

        context = new AnimationContext(display, player, lines * maxWordLength, textColor);

        plugin.getManager().registerDisplay(background);
        plugin.getManager().registerDisplay(display);

        executeNext();
    }

    private void executeNext() {
        if (tasksQueue.isEmpty()) {

            if (loop) {
                tasksSequence.forEach(WriterTask::reset);
                tasksQueue.addAll(tasksSequence);
            } else {
                return;
            }
        }

        Objects.requireNonNull(tasksQueue.poll()).execute(plugin, this::executeNext, context);
    }

    public static class Builder {
        private final List<WriterTask> tasksSequence = new ArrayList<>();

        private int maxWordLength = 0;

        private final StringBuilder wholeText = new StringBuilder();

        private TextColor textColor = NamedTextColor.GREEN;

        private boolean loop = false;

        public Builder type(String text) {
            wholeText.append(text);
            maxWordLength = Math.max(maxWordLength, text.length());

            tasksSequence.add(new TypeTask(text));
            return this;
        }

        public Builder erase(int charsToErase) {
            tasksSequence.add(new EraseTask(charsToErase));
            return this;
        }

        public Builder delete() {
            tasksSequence.add(new DeleteTask());
            return this;
        }

        public Builder pause(int repetitions) {
            tasksSequence.add(new PauseTask(repetitions));
            return this;
        }

        public Builder setTextColor(TextColor color) {
            textColor = color;
            return this;
        }

        public Builder setLoop(boolean loop) {
            this.loop = loop;
            return this;
        }

        public Writer build(Typewriter plugin) {
            return new Writer(plugin,this);
        }
    }
}
