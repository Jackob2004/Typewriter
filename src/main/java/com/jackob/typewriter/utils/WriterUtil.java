package com.jackob.typewriter.utils;

import net.kyori.adventure.text.Component;

import static net.kyori.adventure.text.format.NamedTextColor.GREEN;

public class WriterUtil {

    /**
     *
     * @param chars represents whole text char array
     * @param textEnd points to the end of the text - exclusive
     * @return text component to be displayed excluding erased chars
     */
    public static Component generateTextComponent(char[] chars, int textEnd) {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < textEnd; i++) {
            sb.append(chars[i]);
        }

        return Component.text(sb.toString()).color(GREEN);
    }

}
