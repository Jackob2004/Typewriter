package com.jackob.typewriter.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import static net.kyori.adventure.text.format.NamedTextColor.BLACK;

public class WriterUtil {

    /**
     *
     * @param chars represents whole text char array
     * @param textEnd points to the end of the text - exclusive
     * @param color text color
     * @return text component to be displayed excluding erased chars
     */
    public static Component generateTextComponent(char[] chars, int textEnd, TextColor color) {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < textEnd; i++) {
            sb.append(chars[i]);
        }

        return Component.text(sb.toString()).color(color);
    }

    /**
     *
     * @param world in which it will spawn
     * @param location spawn location
     * @param isBackground will only act as a background
     * @param text determines how big the display is
     * @return spawned display
     */
    public static TextDisplay spawnDisplay(World world, Location location, boolean isBackground, String text) {
        final Component component = Component.text(text).color(BLACK);

        return world.spawn(location, TextDisplay.class, entity -> {
            entity.text(component);
            entity.setBillboard(Display.Billboard.VERTICAL);

            if (isBackground) {
                entity.setBackgroundColor(Color.BLACK);
            } else {
                entity.setSeeThrough(true);
            }
        });
    }

}
