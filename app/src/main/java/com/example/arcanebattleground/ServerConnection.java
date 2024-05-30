package com.example.arcanebattleground;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

    @Override
    public void run() {
        try {
            socket = new Socket("172.30.13.200", 50000);
            oIn = new ObjectInputStream(socket.getInputStream());
            oOut = new ObjectOutputStream(socket.getOutputStream());
            oOut.flush();
            System.err.println("--------------STREAMS OPENED------------------------");
            clientId = oIn.readInt();
            //Log.d("topic", "msg");
            System.err.println("--------------ClientId received------------------------");

            while (!this.isInterrupted()) {
                System.err.println("--------------starting wait------------------------");
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
                }
            }
        } catch (IOException | InterruptedException | ClassNotFoundException e) {

            throw new RuntimeException(e);
        }

    }
}
