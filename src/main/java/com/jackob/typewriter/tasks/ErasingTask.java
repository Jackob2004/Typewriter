package com.jackob.typewriter.tasks;

import com.jackob.typewriter.Typewriter;
import com.jackob.typewriter.objects.AnimationContext;
import com.jackob.typewriter.utils.WriterUtil;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ErasingTask implements WriterTask {

    private final Typewriter plugin;

    private int charsToErase;

    public ErasingTask(Typewriter plugin, int charsToErase) {
        if (charsToErase < 1) {
            throw new IllegalArgumentException("charsToErase must be a positive integer");
        }

        this.plugin = plugin;
        this.charsToErase = charsToErase;
    }

    @Override
    public BukkitTask execute(Runnable onComplete, AnimationContext context) {
        return new BukkitRunnable() {

            @Override
            public void run() {
                if (charsToErase <= 0) {
                    this.cancel();
                    onComplete.run();
                    return;
                }

                context.decrementCurrCharacter();
                charsToErase--;

                context.getDisplay().text(WriterUtil.generateTextComponent(context.getCurrText(), context.getCurrCharacter()));
            }

        }.runTaskTimer(plugin, 0, 12);
    }

}
