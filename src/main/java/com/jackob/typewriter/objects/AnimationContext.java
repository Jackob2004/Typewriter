package com.jackob.typewriter.objects;

import org.bukkit.entity.TextDisplay;

public class AnimationContext {

    private final TextDisplay display;

    private int currCharacter;

    private String currText;

    public AnimationContext(TextDisplay display, int currCharacter, String currText) {
        this.display = display;
        this.currCharacter = currCharacter;
        this.currText = currText;
    }

    public int getCurrCharacter() { return currCharacter; }

    public TextDisplay getDisplay() { return display; }

    public String getCurrText() { return currText; }

    public void setCurrCharacter(int currCharacter) { this.currCharacter = currCharacter; }

    public void setCurrText(String currText) { this.currText = currText; }

}
