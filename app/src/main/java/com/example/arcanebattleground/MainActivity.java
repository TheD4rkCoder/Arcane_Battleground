package com.example.arcanebattleground;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    public static MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;
        setContentView(new MainMenu(this));

        Socket socket;

        try {
            socket = new Socket("?10.10.30.?", 50000);
            ServerConnection.oIn = new ObjectInputStream(socket.getInputStream());
            ServerConnection.oOut = new ObjectOutputStream(socket.getOutputStream());
            ServerConnection.socket = socket;
            ServerConnection.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setContentView(new GameView(this));
    }
}