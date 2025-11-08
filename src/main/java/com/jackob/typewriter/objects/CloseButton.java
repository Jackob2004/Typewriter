package com.jackob.typewriter.objects;

import com.jackob.typewriter.Typewriter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class CloseButton implements Listener {

    private final Typewriter plugin;

    private final Interaction hitbox;

    private final TextDisplay buttonText;

    private final Runnable onClose;

    public CloseButton(Typewriter plugin, Runnable onClose, World world, Location location) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        this.plugin = plugin;
        this.hitbox = spawnHitbox(world, location);
        this.buttonText = spawnText(world, location);
        this.onClose = onClose;
        plugin.getCleanupManager().registerEntities(hitbox, buttonText);
    }

    private Interaction spawnHitbox(World world, Location location) {
        return world.spawn(location, Interaction.class, interaction -> {
            interaction.setResponsive(true);
            interaction.setInteractionHeight(0.3f);
            interaction.setInteractionWidth(0.5f);
        });
    }

    private TextDisplay spawnText(World world, Location location) {
        final Component component = Component.text("Close").color(NamedTextColor.WHITE);

        return world.spawn(location, TextDisplay.class, text -> {
            text.setBillboard(Display.Billboard.VERTICAL);
            text.setBackgroundColor(Color.RED);
            text.text(component);
        });
    }

    private void removeButton() {
        this.hitbox.remove();
        this.buttonText.remove();
        HandlerList.unregisterAll(this);
        plugin.getCleanupManager().unregisterEntities(this.hitbox, this.buttonText);
    }

    @EventHandler
    public void onBtnClick(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Interaction)) return;
        if (e.getRightClicked() != this.hitbox) return;

        removeButton();
        onClose.run();

        final Player player = e.getPlayer();
        player.playSound(player.getLocation(), Sound.BLOCK_IRON_TRAPDOOR_CLOSE, 1.0f, 1.0f);
    }

}
