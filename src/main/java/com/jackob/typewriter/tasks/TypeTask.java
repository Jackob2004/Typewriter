package com.jackob.typewriter.tasks;

import com.jackob.typewriter.Typewriter;
import com.jackob.typewriter.objects.AnimationContext;
import com.jackob.typewriter.utils.WriterUtil;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TypeTask implements WriterTask {

    private final char[] text;

    private int localCurrCharacter;

    public TypeTask(String text) {
        this.text = text.toCharArray();
        this.localCurrCharacter = 0;
    }

    @Override
    public BukkitTask execute(Typewriter plugin, Runnable onComplete, AnimationContext context) {
        return new BukkitRunnable() {

            @Override
            public void run() {
                if (localCurrCharacter > text.length - 1) {
                    this.cancel();
                    onComplete.run();
                    return;
                }

                context.getCurrText()[context.getCurrCharacter()] = text[localCurrCharacter];

                context.getDisplay().text(WriterUtil.generateTextComponent(context.getCurrText(), context.getCurrCharacter() + 1, context.getTextColor()));

                context.incrementCurrCharacter();
                localCurrCharacter++;
                context.getReceiver().playSound(context.getReceiver().getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 1, 1);
            }

        }.runTaskTimer(plugin, 20, 8);
    }

    @Override
    public void reset() {
        localCurrCharacter = 0;
    }

}
