package com.jackob.typewriter.objects;

import com.jackob.typewriter.Typewriter;
import com.jackob.typewriter.tasks.*;

import java.util.LinkedList;
import java.util.Queue;

public class Writer {

    private final Typewriter plugin;

    private final Queue<WriterTask> tasks;

    Writer(Typewriter plugin, Builder builder) {
        this.plugin = plugin;
        this.tasks = builder.tasks;
    }

    public static class Builder {
        private final Queue<WriterTask> tasks = new LinkedList<>();

        public Builder type(String text) {
            tasks.offer(new TypingTask(text));
            return this;
        }

        public Builder erase(int charsToErase) {
            tasks.offer(new ErasingTask(charsToErase));
            return this;
        }

        public Builder delete() {
            tasks.offer(new DeleteTask());
            return this;
        }

        public Builder pause(int repetitions) {
            tasks.offer(new PauseTask(repetitions));
            return this;
        }

        public Writer build(Typewriter plugin) {
            return new Writer(plugin,this);
        }
    }
}
