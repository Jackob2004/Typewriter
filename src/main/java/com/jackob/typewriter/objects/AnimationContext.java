package com.jackob.typewriter.objects;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

public class AnimationContext {

    private final TextDisplay display;

    private final TextDisplay background;

    private final Player receiver;

    private int currCharacter;

    private final char[] currText;

    private final TextColor textColor;

    public AnimationContext(TextDisplay display, TextDisplay background, Player receiver, int textLength, TextColor textColor) {
        this.display = display;
        this.background = background;
        this.receiver = receiver;
        this.currCharacter = 0;
        this.currText = new char[textLength];
        this.textColor = textColor;
    }

    public int getCurrCharacter() { return currCharacter; }

    public TextDisplay getDisplay() { return display; }

    public char[] getCurrText() { return currText; }

    public Player getReceiver() { return receiver; }

    public TextColor getTextColor() { return textColor; }

    public void incrementCurrCharacter() {
        if (currCharacter + 1 < currText.length) {
            currCharacter++;
        }
    }

    public void decrementCurrCharacter() {
        if (currCharacter - 1 >= 0)  {
            this.currCharacter--;
        }
    }

    public void clearContext() {
        display.remove();
        background.remove();
    }

}
