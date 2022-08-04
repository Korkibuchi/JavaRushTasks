package com.javarush.games.moonlander;

import java.util.List;

public class RocketFire extends GameObject{
    private List<int[][]> frames;
    private int frameIndex;
    private boolean isVisible;

    public RocketFire(List<int[][]> frames) {
        super(0, 0, frames.get(0));
        this.frames = frames;
        frameIndex = 0;
        isVisible = false;
    }
}
