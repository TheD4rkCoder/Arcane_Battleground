package com.example.arcanebattleground;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection {
    public static ObjectOutputStream oOut;
    public static ObjectInputStream oIn;
    public static Socket socket;
    public static int clientId;
    public static String playerEntityId;

    public static void getId(){
        try {
            clientId = oIn.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
