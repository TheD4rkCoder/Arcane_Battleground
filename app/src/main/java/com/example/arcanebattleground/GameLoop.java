package com.example.arcanebattleground;

public class GameLoop implements Runnable {
    private Thread t;
    public GameLoop() {
        t = new Thread(this);
        t.start();
    }
    @Override
    public void run() {
        while (true) {
            GameView.drawGame();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
