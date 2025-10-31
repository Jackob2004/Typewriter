package com.jackob.typewriter.tasks;

import com.jackob.typewriter.Typewriter;
import com.jackob.typewriter.objects.AnimationContext;
import com.jackob.typewriter.utils.WriterUtil;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class EraseTask implements WriterTask {

    private final int charsToErase;

    private int currCharsToErase;

    public EraseTask(int charsToErase) {
        if (charsToErase < 1) {
            throw new IllegalArgumentException("charsToErase must be a positive integer");
        }

        this.charsToErase = charsToErase;
        this.currCharsToErase = charsToErase;
    }

    @Override
    public BukkitTask execute(Typewriter plugin, Runnable onComplete, AnimationContext context) {
        return new BukkitRunnable() {

            @Override
            public void run() {
                if (currCharsToErase <= 0) {
                    this.cancel();
                    onComplete.run();
                    return;
                }

                context.decrementCurrCharacter();
                currCharsToErase--;

                context.getDisplay().text(WriterUtil.generateTextComponent(context.getCurrText(), context.getCurrCharacter(), context.getTextColor()));
            }

        }.runTaskTimer(plugin, 20, 8);
    }

    @Override
    public void reset() {
        currCharsToErase = charsToErase;
    }

}
