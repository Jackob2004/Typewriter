package com.jackob.typewriter.tasks;

import com.jackob.typewriter.Typewriter;
import com.jackob.typewriter.objects.AnimationContext;
import com.jackob.typewriter.utils.WriterUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class PauseTask implements WriterTask {

    private final Typewriter plugin;

    private int repetitions;

    private boolean showCursor;

    public PauseTask(Typewriter plugin, int repetitions) {
        this.plugin = plugin;
        this.repetitions = repetitions;
        this.showCursor = true;
    }

    @Override
    public BukkitTask execute(Runnable onComplete, AnimationContext context) {
        return new BukkitRunnable() {

            @Override
            public void run() {
                if (repetitions <= 0) {
                    if (!showCursor) {
                        context.getDisplay().text(WriterUtil.generateTextComponent(context.getCurrText(), context.getCurrCharacter()));
                    }

                    this.cancel();
                    onComplete.run();
                    return;
                }

                Component text = WriterUtil.generateTextComponent(context.getCurrText(), context.getCurrCharacter());

                if (showCursor) {
                    text = text.append(Component.text(" |").color(NamedTextColor.WHITE));
                }

                context.getDisplay().text(text);

                repetitions--;
                showCursor = !showCursor;
            }

        }.runTaskTimer(plugin, 20, 12);
    }

}
