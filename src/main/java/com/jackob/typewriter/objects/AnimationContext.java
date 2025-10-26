package com.jackob.typewriter.objects;

import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

public class AnimationContext {

    private final TextDisplay display;

    private final Player receiver;

    private int currCharacter;

    private final char[] currText;

    public AnimationContext(TextDisplay display, Player receiver, int textLength) {
        this.display = display;
        this.receiver = receiver;
        this.currCharacter = 0;
        this.currText = new char[textLength];
    }

    public int getCurrCharacter() { return currCharacter; }

    public TextDisplay getDisplay() { return display; }

    public char[] getCurrText() { return currText; }

    public Player getReceiver() { return receiver; }

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
