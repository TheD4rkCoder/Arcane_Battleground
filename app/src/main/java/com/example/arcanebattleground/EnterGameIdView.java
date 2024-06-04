package com.example.arcanebattleground;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

public class EnterGameIdView extends LinearLayout {

    private TextView textView;
    private EditText editText;
    private Button button;
    private View backgroundView;
    private MenuFrameLayout menuFrameLayout;

    public EnterGameIdView(Context context, MenuFrameLayout menuFrameLayout) {
        super(context);
        // Set orientation to vertical for stacking elements
        setOrientation(LinearLayout.VERTICAL);
        LinearLayout mainLayout = new LinearLayout(context);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mainLayout.setLayoutParams(params);

        // Create background view
        backgroundView = new View(context);
        backgroundView.setBackgroundColor(getResources().getColor(android.R.color.black, null)); // Black color
        backgroundView.setAlpha(0.5f); // Set transparency (0.0 - fully transparent, 1.0 - fully opaque)
        backgroundView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        // Create TextView
        textView = new TextView(context);
        textView.setText("Enter game id:");
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        textView.setTextColor(getResources().getColor(android.R.color.holo_green_light, null));

        // Create EditText
        editText = new EditText(context);
        editText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        // Create Button
        button = new Button(context);
        button.setText("Join");
        button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        button.setOnClickListener(view -> {
            int gameId = Integer.parseInt(editText.getText().toString());
            Thread t1 = new Thread(() -> {
                try {
                    ServerConnection.oOut.writeInt(gameId);
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
            if(ServerConnection.gameId != -1)
                menuFrameLayout.displayLobbyView();
        });

        // Add views to the layout
        //mainLayout.addView(backgroundView); // Add background view first
        mainLayout.addView(textView);
        mainLayout.addView(editText);
        mainLayout.addView(button);
        this.menuFrameLayout = menuFrameLayout;

        addView(mainLayout);
    }

}

