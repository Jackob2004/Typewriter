package com.jackob.typewriter.tasks;

import com.jackob.typewriter.Typewriter;
import com.jackob.typewriter.objects.AnimationContext;
import com.jackob.typewriter.utils.WriterUtil;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TypingTask implements WriterTask {

    private final Typewriter plugin;

    private final char[] text;

    private int localCurrCharacter;

    public TypingTask(Typewriter plugin, String text) {
        this.plugin = plugin;
        this.text = text.toCharArray();
        this.localCurrCharacter = 0;
    }

    @Override
    public BukkitTask execute(Runnable onComplete, AnimationContext context) {
        return new BukkitRunnable() {

            @Override
            public void run() {
                if (localCurrCharacter > text.length - 1) {
                    this.cancel();
                    onComplete.run();
                    return;
                }

                context.getCurrText()[context.getCurrCharacter()] = text[localCurrCharacter];

                context.getDisplay().text(WriterUtil.generateTextComponent(context.getCurrText(), context.getCurrCharacter() + 1));

                context.incrementCurrCharacter();
                localCurrCharacter++;
            }

        }.runTaskTimer(plugin, 0, 12);
    }

}
