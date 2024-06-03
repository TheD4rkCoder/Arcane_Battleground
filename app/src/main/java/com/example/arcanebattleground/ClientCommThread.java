package com.example.arcanebattleground;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientCommThread extends Thread {
    private final Socket client;

    public ClientCommThread(Socket client) {
        this.client = client;
    }


}
