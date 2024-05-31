package com.example.arcanebattleground;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LobbyView extends LinearLayout {

    public LobbyView(Context context, MainMenu mainMenu) {
        super(context);

        // Initialize main layout
        LinearLayout mainLayout = new LinearLayout(context);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mainLayout.setLayoutParams(params);

        // Create TextView for the top
        TextView topTextView = new TextView(context);
        topTextView.setText("Game id: " + ServerConnection.gameId);
        topTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mainLayout.addView(topTextView);

        if(ServerConnection.isHost){
            // Create a list of TextViews dynamically
            List<String> dataList = new ArrayList<>();
            dataList.add("Item 1");
            dataList.add("Item 2");
            dataList.add("Item 3");

            for (String item : dataList) {
                TextView itemTextView = new TextView(context);
                itemTextView.setText(item);
                itemTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                mainLayout.addView(itemTextView);
            }

            // Create buttons
            Button startButton = new Button(context);
            startButton.setText("Start");
            startButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            startButton.setOnClickListener(view -> {
                Thread t1 = new Thread(() -> {
                    try {
                        ServerConnection.oOut.writeInt(1);//Start game
                        ServerConnection.oOut.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                t1.start();
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (ServerConnection.socket){
                    try {
                        ServerConnection.socket.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                mainMenu.startGame();
            });
            Button cancelButton = new Button(context);
            cancelButton.setText("Cancel");
            cancelButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            cancelButton.setOnClickListener(view -> {
                Thread t1 = new Thread(() -> {
                    try {
                        ServerConnection.oOut.writeInt(0);
                        ServerConnection.oOut.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                t1.start();
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });

            // Add buttons to a horizontal layout
            LinearLayout buttonLayout = new LinearLayout(context);
            buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
            buttonLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            buttonLayout.addView(startButton);
            buttonLayout.addView(cancelButton);

            // Add button layout to the main layout
            mainLayout.addView(buttonLayout);
        }


        // Set the main layout as the content view for this view
        addView(mainLayout);
    }
}
