package com.jackob.typewriter.utils;

import com.destroystokyo.paper.ParticleBuilder;
import com.jackob.typewriter.Typewriter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class SpawnAnimationUtil {

    public static void startSizeAnimation(Runnable onComplete, String text, World world, Location loc, Typewriter plugin) {
        new BukkitRunnable() {
            public void run() {
                final Component component = Component.text(text).color(NamedTextColor.BLACK);
                final TextDisplay display = world.spawn(loc, TextDisplay.class, entity -> {
                    entity.text(component);
                    entity.setBillboard(Display.Billboard.VERTICAL);
                    entity.setBackgroundColor(Color.BLACK);
                });

                final int SCALE_DOWN_SPEED = 5 * 20;
                final int SCALE_UP_SPEED = 3 * 20;

                animateSize(display, plugin, () -> animateSize(display, plugin, () -> {
                    display.remove();
                    onComplete.run();
                }, 1.1f, SCALE_UP_SPEED), 0.1f, SCALE_DOWN_SPEED);
            }
        }.runTaskLater(plugin, 20);
    }

    private static void animateSize(TextDisplay display, Typewriter plugin, Runnable onComplete, float scaleFactor, int speed) {
        final Matrix4f mat = new Matrix4f();

        new BukkitRunnable() {
            int repetitions = 30;

            @Override
            public void run() {
                if (repetitions <= 0 ) {
                    this.cancel();
                    onComplete.run();
                    return;
                }

                display.setTransformationMatrix(mat.scale(scaleFactor));
                display.setInterpolationDelay(0);
                display.setInterpolationDuration(speed);
                repetitions--;
            }
        }.runTaskTimer(plugin, 3, 1);
    }

    public static void spawnTrials(List<Location> startPoints, Location endPoint) {
        for (Location startPoint : startPoints) {
            spawnTrial(startPoint.add(0,1,0), endPoint);
        }
    }

    private static void spawnTrial(Location start, Location end) {
        Particle.TRAIL.builder()
                .location(start)
                .offset(1, 1, 1)
                .count(8)
                .data(new Particle.Trail(end, Color.YELLOW, 20))
                .receivers(32, true)
                .spawn();
    }

    public static List<Location> spawnLines(Location baseLoc, int baseOffset) {
        final int[][] offsets = { {-baseOffset, 0, baseOffset}, {baseOffset, 0, baseOffset}, {-baseOffset, 0, -baseOffset}, {baseOffset, 0, -baseOffset} };
        final List<Location> finalLocations = new ArrayList<>(4);

        for (int[] offset : offsets) {
            final Location finalLocation = baseLoc.clone().add(offset[0], offset[1], offset[2]);
            spawnVerticalLine(finalLocation);
            finalLocations.add(finalLocation);
        }

        return finalLocations;
    }

    private static void spawnVerticalLine(Location location) {
        final ParticleBuilder builder = Particle.DUST_COLOR_TRANSITION.builder()
                .count(5)
                .colorTransition(Color.AQUA, Color.GREEN);

        for (double i = -1.25; i <= 1.25; i += 0.25) {
            builder.location(location.clone().add(0, i, 0)).receivers(15, true).spawn();
        }
    }
}
