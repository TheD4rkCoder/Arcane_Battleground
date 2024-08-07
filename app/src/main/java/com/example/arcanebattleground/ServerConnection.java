package com.example.arcanebattleground;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

import shared.Entity;
import shared.Game;

public class ServerConnection extends Thread {
    public static ObjectOutputStream oOut;
    public static ObjectInputStream oIn;
    public static Socket socket;
    public static int clientId;
    public static String playerEntityId;
    public static Object transfer;
    private static int state = 0;
    public static int gameId = -1;
    public static Game game;
    public static boolean isHost = false;
    public static MainMenu mainMenu = null;
    public static int ownIndex;
    public static boolean offline = true;

    @Override
    public void run() {
        try {
            socket = new Socket("10.171.152.249", 50000);
            offline = false;
            mainMenu.drawMenu();
            System.err.println("------------------Server connection established-------------------------");
            oIn = new ObjectInputStream(socket.getInputStream());
            oOut = new ObjectOutputStream(socket.getOutputStream());
            oOut.flush();
            clientId = oIn.readInt();
            playerEntityId = "player" + clientId;

            while (!this.isInterrupted()) {
                Object obj = oIn.readObject();
                if (obj instanceof Integer) {
                    gameId = (int) obj;
                    synchronized (socket) {
                        socket.notify();
                    }
                } else if (obj instanceof Boolean) {
                    boolean res = (boolean) obj;
                    if (!res)
                        gameId = -1;
                    synchronized (socket) {
                        socket.notify();
                    }
                }else if(obj instanceof String){
                    System.out.println("String");
                }else if (obj instanceof Game){
                    game = (Game) obj;
                    mainMenu.startGame();
                }else if(obj instanceof Entity){
                    Entity e = (Entity) obj;
                    if(!Objects.equals(e.getId(), playerEntityId)){
                        GameView.handleTouchEvent(e.getX() * GameView.screenWidth, e.getY() * GameView.screenHeight);
                    }
                }
                /*
                synchronized (socket) {
                    socket.wait();
                }
                switch (state) {
                    case 0: {//Create or join game
                        oOut.writeObject(transfer);
                        if (transfer == Integer.valueOf(-1)) {
                            gameId = oIn.readInt();
                            Thread.sleep(1000);
                            synchronized (socket) {
                                socket.notify(); // what tis for?
                            }
                            state = 1;
                        } else {
                            boolean res = oIn.readBoolean();
                            if (res) {
                                gameId = (int) transfer;
                                state = 2;
                                Thread.sleep(1000);
                                synchronized (socket) {
                                    socket.notify();
                                }
                            }
                        }
                        break;
                    }
                    case 1: {//start game
                        oOut.writeObject(transfer);
                        state = 2;
                        break;
                    }
                    case 2: {//get game obj from server
                        game = (Game) oIn.readObject();
                        playerEntityId = "player" + clientId;
                        state = 3;
                    }
                }//*/
            }
        } catch (IOException | ClassNotFoundException e) {
            //throw new RuntimeException(e);
        }

    }
}
