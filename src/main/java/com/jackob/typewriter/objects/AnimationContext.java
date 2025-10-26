package com.jackob.typewriter.objects;

import org.bukkit.entity.TextDisplay;

public class AnimationContext {

    private final TextDisplay display;

    private int currCharacter;

    private final char[] currText;

    public AnimationContext(TextDisplay display, int textLength) {
        this.display = display;
        this.currCharacter = 0;
        this.currText = new char[textLength];
    }

    public int getCurrCharacter() { return currCharacter; }

    public TextDisplay getDisplay() { return display; }

    public char[] getCurrText() { return currText; }

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

}
