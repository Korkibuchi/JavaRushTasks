package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

public class MoonLanderGame extends Game {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private int score;
    private boolean isUpPressed;
    private boolean isGameStopped;
    private boolean isLeftPressed;
    private boolean isRightPressed;
    private GameObject platform;
    private Rocket rocket;
    private GameObject landscape;



    @Override
    public void initialize() {
        setScreenSize(WIDTH,HEIGHT);
        createGame();
        showGrid(false);
    }

    private void createGame(){
        createGameObjects();
        setTurnTimer(50);
        drawScene();
        isLeftPressed= false;
        isUpPressed=false;
        isRightPressed=false;
        isGameStopped=false;
        score = 1000;
    }
    private void drawScene(){
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                setCellColor(j,i,Color.ORANGERED);
            }
        }
        rocket.draw(this);
        landscape.draw(this);
    }
    private void createGameObjects(){
        rocket = new Rocket(WIDTH/2, 0);
        landscape= new GameObject(0,25,ShapeMatrix.LANDSCAPE);
        platform = new GameObject(23, MoonLanderGame.HEIGHT-1,ShapeMatrix.PLATFORM);
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (y>=HEIGHT || x>=WIDTH || x<0 || y<0) return;
        super.setCellColor(x, y, color);
    }

    @Override
    public void onTurn(int step) {
        rocket.move(isUpPressed, isLeftPressed, isRightPressed);
        check();
        drawScene();
        if (score > 0) score--;
        setScore(score);
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.UP) isUpPressed = true;
        else if (key == Key.LEFT) {isLeftPressed = true; isRightPressed = false;}
        else if (key == Key.RIGHT) {isLeftPressed = false; isRightPressed = true;}
        if (key == Key.SPACE && isGameStopped){
            createGame(); return;
        }
        }

    @Override
    public void onKeyReleased(Key key) {
        if (key == Key.UP) isUpPressed = false;
        if (key == Key.LEFT) isLeftPressed = false;
        if (key == Key.RIGHT) isRightPressed = false;
    }
    private void check(){
        if (rocket.isCollision(platform))win();
        if (rocket.isCollision(landscape)) gameOver();

    }
    private void win(){
        rocket.land();
        isGameStopped = true;
        showMessageDialog(Color.NONE,"YOU WIN!!!", Color.BLUE, 85);
        stopTurnTimer();
    }
    private void gameOver(){
        rocket.crash();
        isGameStopped=true;
        showMessageDialog(Color.NONE, "GAME OVER!!!", Color.BLUE, 85);
        stopTurnTimer();
        score = 0;
    }
}
