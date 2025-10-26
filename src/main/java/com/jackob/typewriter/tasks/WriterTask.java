package com.jackob.typewriter.tasks;

import com.jackob.typewriter.objects.AnimationContext;
import org.bukkit.scheduler.BukkitTask;

public interface WriterTask {
    BukkitTask execute(Runnable onComplete, AnimationContext context);
}
