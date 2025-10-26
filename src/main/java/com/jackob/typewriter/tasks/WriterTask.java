package com.jackob.typewriter.tasks;

import com.jackob.typewriter.Typewriter;
import com.jackob.typewriter.objects.AnimationContext;
import org.bukkit.scheduler.BukkitTask;

public interface WriterTask {
    BukkitTask execute(Typewriter plugin, Runnable onComplete, AnimationContext context);
}
