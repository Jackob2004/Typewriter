package com.jackob.typewriter.objects;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.TextDisplay;

public class AnimationContext {

    private final TextDisplay display;

    private final TextDisplay background;

    private int currCharacter;

    private final char[] currText;

    private final TextColor textColor;

    public AnimationContext(TextDisplay display, TextDisplay background, int textLength, TextColor textColor) {
        this.display = display;
        this.background = background;
        this.currCharacter = 0;
        this.currText = new char[textLength];
        this.textColor = textColor;
    }

    public int getCurrCharacter() { return currCharacter; }

    public TextDisplay getDisplay() { return display; }

    public char[] getCurrText() { return currText; }

    public TextColor getTextColor() { return textColor; }

    public TextDisplay getBackground() { return background; }

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
