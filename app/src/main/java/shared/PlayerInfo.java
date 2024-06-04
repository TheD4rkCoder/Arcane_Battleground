package shared;


import com.example.arcanebattleground.ClientCommThread;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class PlayerInfo implements Serializable {
    private static final long serialVersionUID = 1567L;
    private transient Socket playerSocket;
    private int id;
    private String name;
    private transient ClientCommThread thread;
    public transient ObjectOutputStream oOut;

    public PlayerInfo(Socket playerSocket, int id, String name, ClientCommThread thread, ObjectOutputStream oOut) {
        this.playerSocket = playerSocket;
        this.id = id;
        this.name = name;
        this.thread = thread;
        this.oOut = oOut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Socket getPlayerSocket() {
        return playerSocket;
    }

}

