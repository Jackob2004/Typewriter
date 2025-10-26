package com.jackob.typewriter.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import static net.kyori.adventure.text.format.NamedTextColor.BLACK;
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

    /**
     *
     * @param player the display will be shown to
     * @param isBackground will only act as a background
     * @param text determines how big the display is
     * @return spawned display
     */
    public static TextDisplay spawnDisplay(Player player, boolean isBackground, String text) {
        final Location location = player.getEyeLocation().add(player.getLocation().getDirection().multiply(2));
        final Component component = Component.text(text).color(BLACK);

        return player.getWorld().spawn(location, TextDisplay.class, entity -> {
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
