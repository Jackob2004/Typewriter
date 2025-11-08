package com.jackob.typewriter.objects;

import com.jackob.typewriter.Typewriter;
import com.jackob.typewriter.tasks.*;
import com.jackob.typewriter.utils.SpawnAnimationUtil;
import com.jackob.typewriter.utils.WriterUtil;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scheduler.BukkitTask;

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

    private BukkitTask task;

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

        final World world = player.getWorld();
        final Location spawnLocation = player.getEyeLocation().add(player.getLocation().getDirection().multiply(5));
        final List<Location> trialsLocations = SpawnAnimationUtil.spawnLines(spawnLocation, 2);

        SpawnAnimationUtil.spawnTrials(trialsLocations, spawnLocation);
        player.playSound(spawnLocation, Sound.BLOCK_ENDER_CHEST_OPEN, 1.0f, 1.0f);
        SpawnAnimationUtil.startSizeAnimation(() -> {
            final TextDisplay background = WriterUtil.spawnDisplay(world, spawnLocation, true, invisibleText);
            final TextDisplay display = WriterUtil.spawnDisplay(world, spawnLocation, false, "");

            context = new AnimationContext(display, background, lines * maxWordLength, textColor);
            new CloseButton(plugin, this::stop, player.getWorld(), spawnLocation.clone().add(0, -0.5f, 0));
            plugin.getCleanupManager().registerEntities(context.getDisplay(), context.getBackground());

            executeNext();
        }, invisibleText, player.getWorld(), spawnLocation, plugin);
    }

    private void stop() {
        if (task != null && !task.isCancelled()) {
            task.cancel();
            task = null;
        }

        context.clearContext();
        plugin.getCleanupManager().unregisterEntities(context.getDisplay(), context.getBackground());
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

        task = Objects.requireNonNull(tasksQueue.poll()).execute(plugin, this::executeNext, context);
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
