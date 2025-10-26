package com.jackob.typewriter.tasks;

import com.jackob.typewriter.Typewriter;
import com.jackob.typewriter.objects.AnimationContext;
import com.jackob.typewriter.utils.WriterUtil;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class DeleteTask implements WriterTask {

    @Override
    public BukkitTask execute(Typewriter plugin, Runnable onComplete, AnimationContext context) {
        return new BukkitRunnable() {

            @Override
            public void run() {
                if (context.getCurrCharacter() == 0) {
                    this.cancel();
                    onComplete.run();
                    return;
                }

                context.decrementCurrCharacter();
                context.getDisplay().text(WriterUtil.generateTextComponent(context.getCurrText(), context.getCurrCharacter()));
            }

        }.runTaskTimer(plugin, 20, 2);
    }
}
