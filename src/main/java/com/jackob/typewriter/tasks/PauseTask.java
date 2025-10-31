package com.jackob.typewriter.tasks;

import com.jackob.typewriter.Typewriter;
import com.jackob.typewriter.objects.AnimationContext;
import com.jackob.typewriter.utils.WriterUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class PauseTask implements WriterTask {

    private final int repetitions;

    private int currRepetitions;

    private boolean showCursor;

    public PauseTask(int repetitions) {
        if (repetitions < 1) {
            throw new IllegalArgumentException("repetitions must be a positive integer");
        }

        this.repetitions = repetitions;
        this.currRepetitions = repetitions;
        this.showCursor = true;
    }

    @Override
    public BukkitTask execute(Typewriter plugin, Runnable onComplete, AnimationContext context) {
        return new BukkitRunnable() {

            @Override
            public void run() {
                if (currRepetitions <= 0) {
                    if (!showCursor) {
                        context.getDisplay().text(WriterUtil.generateTextComponent(context.getCurrText(), context.getCurrCharacter(), context.getTextColor()));
                    }

                    this.cancel();
                    onComplete.run();
                    return;
                }

                Component text = WriterUtil.generateTextComponent(context.getCurrText(), context.getCurrCharacter(), context.getTextColor());

                if (showCursor) {
                    text = text.append(Component.text(" |").color(NamedTextColor.WHITE));
                }

                context.getDisplay().text(text);

                currRepetitions--;
                showCursor = !showCursor;
            }

        }.runTaskTimer(plugin, 20, 12);
    }

    @Override
    public void reset() {
        currRepetitions = repetitions;
        showCursor = true;
    }

}
